package Gift4U.Backend.survey.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Gift4U.Backend.apiPayload.code.status.ErrorStatus;
import Gift4U.Backend.apiPayload.exception.GeneralException;
import Gift4U.Backend.survey.converter.SurveyConverter;
import Gift4U.Backend.survey.domain.AskRecommendation;
import Gift4U.Backend.survey.repository.SurveyRepository;
import Gift4U.Backend.survey.web.dto.SurveyResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SurveyServiceImpl implements SurveyService{

	private final SurveyRepository surveyRepository;

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
}
