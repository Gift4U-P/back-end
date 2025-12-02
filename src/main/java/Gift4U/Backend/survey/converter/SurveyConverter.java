package Gift4U.Backend.survey.converter;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import Gift4U.Backend.apiPayload.code.status.ErrorStatus;
import Gift4U.Backend.apiPayload.exception.GeneralException;
import Gift4U.Backend.survey.domain.AskRecommendation;
import Gift4U.Backend.survey.web.dto.SurveyResponseDTO;

public class SurveyConverter {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	// 설문 결과 목록 조회 API
	public static SurveyResponseDTO.SurveyResult toSurveyListResult(AskRecommendation survey) {
		return SurveyResponseDTO.SurveyResult.builder()
			.surveyId(survey.getId())
			.savedName(survey.getSavedName())
			.createdAt(survey.getCreatedAt())
			.build();
	}

	// 설문 결과 상세 조회 API
	public static SurveyResponseDTO.SurveyDetailResult toSurveyDetailResult(AskRecommendation survey) {
		try {
			// JSON 파싱
			List<SurveyResponseDTO.SurveyDetailResult.GiftList> giftLists =
				objectMapper.readValue(survey.getPresentRecommend(), new TypeReference<>() {
				});

			// DTO 반환
			return SurveyResponseDTO.SurveyDetailResult.builder()
				.savedName(survey.getSavedName())
				.analysis(survey.getCharacterText())
				.reasoning(survey.getCharacterType())
				.card_message(survey.getRecommendText())
				.giftList(giftLists)
				.build();

		} catch (JsonProcessingException e) {
			throw new GeneralException(ErrorStatus.JSON_PARSE_ERROR);
		}
	}

	// 설문 추천 결과 저장 API
	public static SurveyResponseDTO.SurveySaveResponse toSurveySaveResponse(AskRecommendation survey) {
		return SurveyResponseDTO.SurveySaveResponse.builder()
			.id(survey.getId())
			.savedName(survey.getSavedName())
			.createdAt(LocalDateTime.from(survey.getCreatedAt()))
			.build();
	}
}
