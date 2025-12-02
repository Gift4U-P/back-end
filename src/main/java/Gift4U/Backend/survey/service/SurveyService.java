package Gift4U.Backend.survey.service;

import Gift4U.Backend.survey.web.dto.SurveyRequestDTO;
import Gift4U.Backend.survey.web.dto.SurveyResponseDTO;

public interface SurveyService {

	// 설문 결과 목록 조회 API
	SurveyResponseDTO.SurveyResultList searchSurveyList(Long userId);

	// 설문 결과 상세 조회 API
	SurveyResponseDTO.SurveyDetailResult searchSurveyDetail(Long userId, Long surveyId);

	// 설문 결과 조회 API
	SurveyResponseDTO.SurveyQuestionResponse searchSurvey(Long userId, SurveyRequestDTO.SurveyResultRequest request);

	// 설문 추천 결과 저장 API
	SurveyResponseDTO.SurveySaveResponse saveSurvey(Long userId, SurveyRequestDTO.SurveySaveRequest request);
}
