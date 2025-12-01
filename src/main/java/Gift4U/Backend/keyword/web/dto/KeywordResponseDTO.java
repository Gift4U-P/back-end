package Gift4U.Backend.keyword.web.dto;

import java.time.LocalDateTime;
import java.util.List;

import Gift4U.Backend.survey.web.dto.SurveyResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
