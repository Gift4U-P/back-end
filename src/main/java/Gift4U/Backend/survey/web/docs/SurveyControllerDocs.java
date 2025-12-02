package Gift4U.Backend.survey.web.docs;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

import Gift4U.Backend.apiPayload.ApiResponse;
import Gift4U.Backend.security.auth.userDetails.CustomUserDetails;
import Gift4U.Backend.survey.web.dto.SurveyRequestDTO;
import Gift4U.Backend.survey.web.dto.SurveyResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Survey", description = "설문 추천 CRUD API")
public interface SurveyControllerDocs {

	@Operation(
		summary = "설문 결과 목록 조회",
		description = "마이페이지에서 설문 추천 결과 목록을 조회합니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SurveyResponseDTO.SurveyResult.class)))
		}
	)
	ResponseEntity<ApiResponse<SurveyResponseDTO.SurveyResultList>> surveyList(
		@AuthenticationPrincipal CustomUserDetails userDetails);

	@Operation(
		summary = "설문 추천 결과 상세 조회",
		description = "마이페이지에서 설문 추천 결과를 상세 조회합니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SurveyResponseDTO.SurveyDetailResult.class))),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SURVEY4001", description = "존재하지 않는 surveyId 입니다.")
		}
	)
	ResponseEntity<ApiResponse<SurveyResponseDTO.SurveyDetailResult>> surveyDetail(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestBody SurveyRequestDTO.SurveyRequest request);

	@Operation(
		summary = "설문 추천 결과 조회",
		description = "설문 결과를 분석하여 선물을 추천합니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SurveyResponseDTO.SurveyQuestionResponse.class)))
		}
	)
	ResponseEntity<ApiResponse<SurveyResponseDTO.SurveyQuestionResponse>> survey(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestBody SurveyRequestDTO.SurveyResultRequest request);

	@Operation(
		summary = "설문 추천 결과 저장",
		description = "설문 추천 결과를 저장합니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SurveyResponseDTO.SurveySaveResponse.class)))
		}
	)
	ResponseEntity<ApiResponse<SurveyResponseDTO.SurveySaveResponse>> saveSurvey(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestBody SurveyRequestDTO.SurveySaveRequest request);
}
