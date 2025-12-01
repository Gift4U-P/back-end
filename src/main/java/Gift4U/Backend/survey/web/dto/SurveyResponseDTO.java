package Gift4U.Backend.survey.web.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SurveyResponseDTO {

	// 설문 결과 목록 조회 API
	@Builder
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SurveyResult {
		private Long surveyId;
		private String savedName;
		private LocalDateTime createdAt;
	}

	@Builder
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SurveyResultList {
		private List<SurveyResult> result;
	}

	// 설문 결과 상세 조회 API
	@Builder
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SurveyDetailResult {
		private String savedName;
		private String analysis;
		private String reasoning;
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
