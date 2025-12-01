package Gift4U.Backend.keyword.web.docs;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

import Gift4U.Backend.apiPayload.ApiResponse;
import Gift4U.Backend.keyword.web.dto.KeywordRequestDTO;
import Gift4U.Backend.keyword.web.dto.KeywordResponseDTO;
import Gift4U.Backend.security.auth.userDetails.CustomUserDetails;
import Gift4U.Backend.survey.web.dto.SurveyResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Keyword", description = "키워드 추천 CRUD API")
public interface KeywordControllerDocs {

	@Operation(
		summary = "키워드 결과 목록 조회",
		description = "마이페이지에서 키워드 추천 결과 목록을 조회합니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = KeywordResponseDTO.KeywordResult.class)))
		}
	)
	ResponseEntity<ApiResponse<KeywordResponseDTO.KeywordResultList>> keywordList(
		@AuthenticationPrincipal CustomUserDetails userDetails);

	@Operation(
		summary = "키워드 추천 결과 상세 조회",
		description = "마이페이지에서 키워드 추천 결과를 상세 조회합니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = KeywordResponseDTO.KeywordDetailResult.class))),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SURVEY4001", description = "존재하지 않는 keywordId 입니다.")
		}
	)
	ResponseEntity<ApiResponse<KeywordResponseDTO.KeywordDetailResult>> keywordDetail(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestBody KeywordRequestDTO.KeywordRequest request);
}
