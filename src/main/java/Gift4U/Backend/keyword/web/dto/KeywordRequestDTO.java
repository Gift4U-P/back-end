package Gift4U.Backend.keyword.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class KeywordRequestDTO {

	// 키워드 결과 상세 조회 API
	@Getter
	@Setter
	public static class KeywordRequest {
		private Long keywordId;
	}

	// 키워드 추천 결과 조회 요청 DTO
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class KeywordResultRequest {
		private String age;
		private String gender;
		private String relationship;
		private String situation;
	}

	// 키워드 추천 결과 저장 요청 DTO
	@Getter
	@Setter
	public static class KeywordSaveRequest {
		private String savedName;
	}
}
