package Gift4U.Backend.keyword.service;

import Gift4U.Backend.keyword.web.dto.KeywordResponseDTO;

public interface KeywordService {

	// 키워드 결과 목록 조회 API
	KeywordResponseDTO.KeywordResultList searchKeywordList(Long userId);

	// 키워드 결과 상세 조회 API
	KeywordResponseDTO.KeywordDetailResult searchKeywordDetail(Long userId, Long keywordId);
}
