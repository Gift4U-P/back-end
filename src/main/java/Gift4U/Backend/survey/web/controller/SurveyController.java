package Gift4U.Backend.survey.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Gift4U.Backend.apiPayload.ApiResponse;
import Gift4U.Backend.apiPayload.code.status.SuccessStatus;
import Gift4U.Backend.security.auth.userDetails.CustomUserDetails;
import Gift4U.Backend.survey.service.SurveyService;
import Gift4U.Backend.survey.web.docs.SurveyControllerDocs;
import Gift4U.Backend.survey.web.dto.SurveyRequestDTO;
import Gift4U.Backend.survey.web.dto.SurveyResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/survey")
public class SurveyController implements SurveyControllerDocs {

	private final SurveyService surveyService;

	// 설문 결과 목록 조회 API
	@GetMapping("/result/list")
	public ResponseEntity<ApiResponse<SurveyResponseDTO.SurveyResultList>> surveyList(
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		Long userId = userDetails.getUserId();
		SurveyResponseDTO.SurveyResultList result = surveyService.searchSurveyList(userId);
		return new ResponseEntity<>(ApiResponse.of(SuccessStatus._OK, result), HttpStatus.CREATED);
	}

	// 설문 결과 상세 조회 API
	@PostMapping("/detailResult")
	public ResponseEntity<ApiResponse<SurveyResponseDTO.SurveyDetailResult>> surveyDetail(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestBody SurveyRequestDTO.SurveyRequest request) {
		Long userId = userDetails.getUserId();
		Long surveyId = request.getSurveyId();
		SurveyResponseDTO.SurveyDetailResult result = surveyService.searchSurveyDetail(userId, surveyId);
		return new ResponseEntity<>(ApiResponse.of(SuccessStatus._OK, result), HttpStatus.CREATED);
	}

	// 설문 추천 결과 조회 API
	@PostMapping("/result")
	public ResponseEntity<ApiResponse<SurveyResponseDTO.SurveyQuestionResponse>> survey(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestBody SurveyRequestDTO.SurveyResultRequest request) {

		SurveyResponseDTO.SurveyQuestionResponse result = surveyService.searchSurvey(userDetails.getUserId(), request);
		return new ResponseEntity<>(ApiResponse.of(SuccessStatus._OK, result), HttpStatus.CREATED);
	}

	// 설문 추천 결과 저장 API
	@PostMapping("/save")
	public ResponseEntity<ApiResponse<SurveyResponseDTO.SurveySaveResponse>> saveSurvey(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestBody SurveyRequestDTO.SurveySaveRequest request) {

		SurveyResponseDTO.SurveySaveResponse result = surveyService.saveSurvey(userDetails.getUserId(), request);
		return new ResponseEntity<>(ApiResponse.of(SuccessStatus._OK, result), HttpStatus.CREATED);
	}
}
