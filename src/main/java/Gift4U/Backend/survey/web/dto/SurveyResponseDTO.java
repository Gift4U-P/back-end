package Gift4U.Backend.survey.web.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

	// 설문 추천 결과 조회 응답 DTO
	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SurveyQuestionResponse {
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

	// 설문 추천 결과 저장 응답 DTO
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SurveySaveResponse {
		private Long id;
		private String savedName;
		private LocalDateTime createdAt;
	}

	// Redis 저장용 DTO
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SurveyRedisDTO {
		@JsonProperty("q1")
		private String qOne;
		@JsonProperty("q2")
		private String qTwo;
		@JsonProperty("q3")
		private String qThree;
		@JsonProperty("q4")
		private String qFour;
		@JsonProperty("q5")
		private String qFive;
		@JsonProperty("q6")
		private String qSix;
		@JsonProperty("q7")
		private String qSeven;
		@JsonProperty("q8")
		private String qEight;
		@JsonProperty("q9")
		private String qNine;
		@JsonProperty("q10")
		private String qTen;

		private String analysis;
		private String reasoning;
		private String card_message;
		private List<SurveyQuestionResponse.GiftList> giftList;
	}
}
