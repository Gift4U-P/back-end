package Gift4U.Backend.survey.service;

import Gift4U.Backend.survey.web.dto.SurveyResponseDTO;

public interface SurveyService {

	// 설문 결과 목록 조회 API
	SurveyResponseDTO.SurveyResultList searchSurveyList(Long userId);

	// 설문 결과 상세 조회 API
	SurveyResponseDTO.SurveyDetailResult searchSurveyDetail(Long userId, Long surveyId);
}
