package Gift4U.Backend.keyword.web.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class KeywordResponseDTO {

	// 키워드 결과 목록 조회 API
	@Builder
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class KeywordResult {
		private Long keywordId;
		private String savedName;
		private LocalDateTime createdAt;
	}

	@Builder
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class KeywordResultList {
		private List<KeywordResponseDTO.KeywordResult> result;
	}

	// 키워드 결과 상세 조회 API
	@Builder
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class KeywordDetailResult {
		private String savedName;
		private String age;
		private String gender;
		private String relationship;
		private String situation;
		private String keywordText;
		private String card_message;
		private List<GiftList> giftList;

		@Getter
		@Builder
		@AllArgsConstructor
		@NoArgsConstructor
		public static class GiftList {
			private String title;
			private String lprice;
			private String link;
			private String image;
			private String mallName;
		}
	}

	// 키워드 추천 결과 조회 응답 DTO
	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class KeywordQuestionResponse {
		private String age;
		private String gender;
		private String relationship;
		private String situation;
		private String keywordText;
		private String card_message;
		private List<GiftList> giftList;

		@Getter
		@Builder
		@AllArgsConstructor
		@NoArgsConstructor
		public static class GiftList {
			private String title;
			private String lprice;
			private String link;
			private String image;
			private String mallName;
		}
	}

	// 키워드 추천 결과 저장 응답 DTO
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class KeywordSaveResponse {
		private Long id;
		private String savedName;
		private LocalDateTime createdAt;
	}

	// Redis 저장용 DTO
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class KeywordRedisDTO {
		private String age;
		private String gender;
		private String relationship;
		private String situation;
		private String keywordText;
		private String card_message;
		private List<KeywordQuestionResponse.GiftList> giftList;
	}
}
