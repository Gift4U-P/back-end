package Gift4U.Backend.keyword.web.controller;

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
import Gift4U.Backend.keyword.service.KeywordService;
import Gift4U.Backend.keyword.web.docs.KeywordControllerDocs;
import Gift4U.Backend.keyword.web.dto.KeywordRequestDTO;
import Gift4U.Backend.keyword.web.dto.KeywordResponseDTO;
import Gift4U.Backend.security.auth.userDetails.CustomUserDetails;
import Gift4U.Backend.survey.web.dto.SurveyRequestDTO;
import Gift4U.Backend.survey.web.dto.SurveyResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/keyword")
public class KeywordController implements KeywordControllerDocs {

	private final KeywordService keywordService;

	// 키워드 결과 목록 조회 API
	@GetMapping("/result/list")
	public ResponseEntity<ApiResponse<KeywordResponseDTO.KeywordResultList>> keywordList(
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		Long userId = userDetails.getUserId();
		KeywordResponseDTO.KeywordResultList result = keywordService.searchKeywordList(userId);
		return new ResponseEntity<>(ApiResponse.of(SuccessStatus._OK, result), HttpStatus.CREATED);
	}

	// 키워드 결과 상세 조회 API
	@PostMapping("/detailResult")
	public ResponseEntity<ApiResponse<KeywordResponseDTO.KeywordDetailResult>> keywordDetail(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestBody KeywordRequestDTO.KeywordRequest request) {
		Long userId = userDetails.getUserId();
		Long keywordId = request.getKeywordId();
		KeywordResponseDTO.KeywordDetailResult result = keywordService.searchKeywordDetail(userId, keywordId);
		return new ResponseEntity<>(ApiResponse.of(SuccessStatus._OK, result), HttpStatus.CREATED);
	}

	// 키워드 추천 결과 조회 API
	@PostMapping("/result")
	public ResponseEntity<ApiResponse<KeywordResponseDTO.KeywordQuestionResponse>> keyword(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestBody KeywordRequestDTO.KeywordResultRequest request) {

		KeywordResponseDTO.KeywordQuestionResponse result = keywordService.searchKeyword(userDetails.getUserId(), request);
		return new ResponseEntity<>(ApiResponse.of(SuccessStatus._OK, result), HttpStatus.CREATED);
	}

	// 키워드 추천 결과 저장 API
	@PostMapping("/save")
	public ResponseEntity<ApiResponse<KeywordResponseDTO.KeywordSaveResponse>> saveKeyword(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestBody KeywordRequestDTO.KeywordSaveRequest request) {

		KeywordResponseDTO.KeywordSaveResponse result = keywordService.saveKeyword(userDetails.getUserId(), request);
		return new ResponseEntity<>(ApiResponse.of(SuccessStatus._OK, result), HttpStatus.CREATED);
	}
}
