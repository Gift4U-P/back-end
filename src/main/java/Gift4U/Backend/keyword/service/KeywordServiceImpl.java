package Gift4U.Backend.keyword.service;

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
import Gift4U.Backend.keyword.converter.KeywordConverter;
import Gift4U.Backend.keyword.domain.KeywordRecommendation;
import Gift4U.Backend.keyword.repository.KeywordRepository;
import Gift4U.Backend.keyword.web.dto.KeywordRequestDTO;
import Gift4U.Backend.keyword.web.dto.KeywordResponseDTO;
import Gift4U.Backend.user.domain.User;
import Gift4U.Backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class KeywordServiceImpl implements KeywordService {

	private final KeywordRepository keywordRepository;
	private final ObjectMapper objectMapper;
	private final FastApiClient fastApiClient;
	private final StringRedisTemplate stringRedisTemplate;
	private final UserRepository userRepository;

	// 키워드 결과 목록 조회 API
	@Override
	public KeywordResponseDTO.KeywordResultList searchKeywordList(Long userId) {
		List<KeywordRecommendation> keywordList = keywordRepository.findAllByUserId(userId);
		List<KeywordResponseDTO.KeywordResult> results = keywordList.stream()
			.map(KeywordConverter::toKeywordListResult)
			.collect(Collectors.toList());

		return KeywordResponseDTO.KeywordResultList.builder()
			.result(results)
			.build();
	}

	// 키워드 결과 상세 조회 API
	@Override
	public KeywordResponseDTO.KeywordDetailResult searchKeywordDetail(Long userId, Long keywordId) {
		KeywordRecommendation keyword = keywordRepository.findByIdAndUserId(keywordId, userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.KEYWORD_NOT_EXIST_ERROR));

		return KeywordConverter.toKeywordDetailResult(keyword);
	}

	// 키워드 추천 결과 조회 API
	@Override
	public KeywordResponseDTO.KeywordQuestionResponse searchKeyword(Long userId, KeywordRequestDTO.KeywordResultRequest request) {

		// FastAPI 호출
		FastApiRequest.KeywordRequest fastApiRequest = new FastApiRequest.KeywordRequest(
			request.getAge(),
			request.getGender(),
			request.getRelationship(),
			request.getSituation()
		);

		FastApiResponse.SearchKeywordList fastApiResponse = fastApiClient.getKeywordRecommend(fastApiRequest);

		// 결과 매핑
		KeywordResponseDTO.KeywordQuestionResponse response = KeywordResponseDTO.KeywordQuestionResponse.builder()
			.age(fastApiResponse.getAge())
			.gender(fastApiResponse.getGender())
			.relationship(fastApiResponse.getRelationship())
			.situation(fastApiResponse.getSituation())
			.keywordText(fastApiResponse.getKeywordText())
			.card_message(fastApiResponse.getCard_message())
			.giftList(
				fastApiResponse.getGiftList().stream()
					.map(r -> KeywordResponseDTO.KeywordQuestionResponse.GiftList.builder()
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
		KeywordResponseDTO.KeywordRedisDTO redisDTO = KeywordResponseDTO.KeywordRedisDTO.builder()
			.age(request.getAge())
			.gender(request.getGender())
			.relationship(request.getRelationship())
			.situation(request.getSituation())
			.keywordText(response.getKeywordText())
			.card_message(response.getCard_message())
			.giftList(response.getGiftList())
			.build();

		// Redis에 저장
		try {
			String redisKey = "keyword:result:" + userId;
			String json = objectMapper.writeValueAsString(redisDTO);
			stringRedisTemplate.opsForValue().set(redisKey, json, Duration.ofMinutes(15));
		} catch (JsonProcessingException e) {
			throw new GeneralException(ErrorStatus.SAVE_REDIS_ERROR);
		}

		return response;
	}

	// 키워드 추천 결과 저장 API
	@Override
	public KeywordResponseDTO.KeywordSaveResponse saveKeyword(Long userId, KeywordRequestDTO.KeywordSaveRequest request) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.EMAIL_USER_NOT_MATCH));

		// Redis에서 결과 JSON 조회
		String redisKey = "keyword:result:" + userId;
		String resultJson = stringRedisTemplate.opsForValue().get(redisKey);
		if (resultJson == null) {
			throw new GeneralException(ErrorStatus.KEYWORD_REDIS_KEY_EXPIRED);
		}

		// JSON → DTO 역직렬화
		KeywordResponseDTO.KeywordRedisDTO redisResult;
		try {
			redisResult = objectMapper.readValue(resultJson, KeywordResponseDTO.KeywordRedisDTO.class);
		} catch (JsonProcessingException e) {
			throw new GeneralException(ErrorStatus.JSON_PARSE_ERROR);
		}

		KeywordRecommendation keyword = KeywordRecommendation.builder()
			.user(user)
			.savedName(request.getSavedName())
			.age(redisResult.getAge())
			.gender(redisResult.getGender())
			.relationship(redisResult.getRelationship())
			.situation(redisResult.getSituation())
			.keywordText(redisResult.getKeywordText())
			.recommendText(redisResult.getCard_message())
			.presentRecommend(JsonUtils.toJson(redisResult.getGiftList()))
			.build();

		KeywordRecommendation saved = keywordRepository.save(keyword);

		// Redis 삭제
		stringRedisTemplate.delete(redisKey);

		return KeywordConverter.toKeywordSaveResponse(saved);
	}
}
