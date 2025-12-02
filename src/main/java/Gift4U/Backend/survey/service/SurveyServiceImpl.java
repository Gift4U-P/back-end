package Gift4U.Backend.survey.service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Gift4U.Backend.apiPayload.code.status.ErrorStatus;
import Gift4U.Backend.apiPayload.exception.GeneralException;
import Gift4U.Backend.common.utils.JsonUtils;
import Gift4U.Backend.external.FastApiClient;
import Gift4U.Backend.external.dto.FastApiRequest;
import Gift4U.Backend.external.dto.FastApiResponse;
import Gift4U.Backend.survey.converter.SurveyConverter;
import Gift4U.Backend.survey.domain.AskRecommendation;
import Gift4U.Backend.survey.repository.SurveyRepository;
import Gift4U.Backend.survey.web.dto.SurveyRequestDTO;
import Gift4U.Backend.survey.web.dto.SurveyResponseDTO;
import Gift4U.Backend.user.domain.User;
import Gift4U.Backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SurveyServiceImpl implements SurveyService{

	private final ObjectMapper objectMapper;
	private final SurveyRepository surveyRepository;
	private final FastApiClient fastApiClient;
	private final StringRedisTemplate stringRedisTemplate;
	private final UserRepository userRepository;

	// 설문 결과 목록 조회 API
	@Override
	public SurveyResponseDTO.SurveyResultList searchSurveyList(Long userId) {
		List<AskRecommendation> surveyList = surveyRepository.findAllByUserId(userId);
		List<SurveyResponseDTO.SurveyResult> results = surveyList.stream()
			.map(SurveyConverter::toSurveyListResult)
			.collect(Collectors.toList());

		return SurveyResponseDTO.SurveyResultList.builder()
			.result(results)
			.build();
	}

	// 설문 결과 상세 조회 API
	@Override
	public SurveyResponseDTO.SurveyDetailResult searchSurveyDetail(Long userId, Long surveyId) {
		AskRecommendation survey = surveyRepository.findByIdAndUserId(surveyId, userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.SURVEY_NOT_EXIST_ERROR));

		return SurveyConverter.toSurveyDetailResult(survey);
	}

	// 설문 추천 결과 조회 API
	@Override
	public SurveyResponseDTO.SurveyQuestionResponse searchSurvey(Long userId, SurveyRequestDTO.SurveyResultRequest request) {

		// FastAPI 호출
		FastApiRequest.SurveyRequest fastApiRequest = new FastApiRequest.SurveyRequest(
			request.getQOne(),
			request.getQTwo(),
			request.getQThree(),
			request.getQFour(),
			request.getQFive(),
			request.getQSix(),
			request.getQSeven(),
			request.getQEight(),
			request.getQNine(),
			request.getQTen()
		);

		FastApiResponse.SearchSurveyList fastApiResponse = fastApiClient.getSurveyRecommend(fastApiRequest);

		// 결과 매핑
		SurveyResponseDTO.SurveyQuestionResponse response = SurveyResponseDTO.SurveyQuestionResponse.builder()
			.analysis(fastApiResponse.getAnalysis())
			.reasoning(fastApiResponse.getReasoning())
			.card_message(fastApiResponse.getCard_message())
			.giftList(
				fastApiResponse.getGiftList().stream()
					.map(r -> SurveyResponseDTO.SurveyQuestionResponse.GiftList.builder()
						.title(r.getTitle())
						.lprice(r.getLprice())
						.link(r.getLink())
						.image(r.getImage())
						.mallName(r.getMallName())
						.build())
					.collect(Collectors.toList())
			)
			.build();

		// Redis에 저장할 DTO 생성
		SurveyResponseDTO.SurveyRedisDTO redisDTO = SurveyResponseDTO.SurveyRedisDTO.builder()
			.qOne(request.getQOne())
			.qTwo(request.getQTwo())
			.qThree(request.getQThree())
			.qFour(request.getQFour())
			.qFive(request.getQFive())
			.qSix(request.getQSix())
			.qSeven(request.getQSeven())
			.qEight(request.getQEight())
			.qNine(request.getQNine())
			.qTen(request.getQTen())
			.analysis(response.getAnalysis())
			.reasoning(response.getReasoning())
			.card_message(response.getCard_message())
			.giftList(response.getGiftList())
			.build();

		// Redis에 저장
		try {
			String redisKey = "survey:result:" + userId;
			String json = objectMapper.writeValueAsString(redisDTO);
			stringRedisTemplate.opsForValue().set(redisKey, json, Duration.ofMinutes(15));
		} catch (JsonProcessingException e) {
			throw new GeneralException(ErrorStatus.SAVE_REDIS_ERROR);
		}

		return response;
	}

	// 설문 추천 결과 저장 API
	@Override
	public SurveyResponseDTO.SurveySaveResponse saveSurvey(Long userId, SurveyRequestDTO.SurveySaveRequest request) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.EMAIL_USER_NOT_MATCH));

		// Redis에서 결과 JSON 조회
		String redisKey = "survey:result:" + userId;
		String resultJson = stringRedisTemplate.opsForValue().get(redisKey);
		if (resultJson == null) {
			throw new GeneralException(ErrorStatus.SURVEY_REDIS_KEY_EXPIRED);
		}

		// JSON → DTO 역직렬화
		SurveyResponseDTO.SurveyRedisDTO redisResult;
		try {
			redisResult = objectMapper.readValue(resultJson, SurveyResponseDTO.SurveyRedisDTO.class);
		} catch (JsonProcessingException e) {
			throw new GeneralException(ErrorStatus.JSON_PARSE_ERROR);
		}

		AskRecommendation survey = AskRecommendation.builder()
			.user(user)
			.savedName(request.getSavedName())
			.characterText(redisResult.getAnalysis())
			.characterType(redisResult.getReasoning())
			.recommendText(redisResult.getCard_message())
			.presentRecommend(JsonUtils.toJson(redisResult.getGiftList()))
			.build();

		AskRecommendation saved = surveyRepository.save(survey);

		// Redis 삭제
		stringRedisTemplate.delete(redisKey);

		return SurveyConverter.toSurveySaveResponse(saved);
	}
}
