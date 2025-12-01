package Gift4U.Backend.keyword.web.dto;

import lombok.Getter;
import lombok.Setter;

public class KeywordRequestDTO {

	// 키워드 결과 상세 조회 API
	@Getter
	@Setter
	public static class KeywordRequest {
		private Long keywordId;
	}
}
