package Gift4U.Backend.keyword.service;

import Gift4U.Backend.keyword.web.dto.KeywordRequestDTO;
import Gift4U.Backend.keyword.web.dto.KeywordResponseDTO;

public interface KeywordService {

	// 키워드 결과 목록 조회 API
	KeywordResponseDTO.KeywordResultList searchKeywordList(Long userId);

	// 키워드 결과 상세 조회 API
	KeywordResponseDTO.KeywordDetailResult searchKeywordDetail(Long userId, Long keywordId);

	// 키워드 추천 결과 조회 API
	KeywordResponseDTO.KeywordQuestionResponse searchKeyword(Long userId, KeywordRequestDTO.KeywordResultRequest request);

	// 키워드 추천 결과 저장 API
	KeywordResponseDTO.KeywordSaveResponse saveKeyword(Long userId, KeywordRequestDTO.KeywordSaveRequest request);
}
