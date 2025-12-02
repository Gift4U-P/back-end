package Gift4U.Backend.survey.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class SurveyRequestDTO {

	// 설문 결과 상세 조회 API
	@Getter
	@Setter
	public static class SurveyRequest {
		private Long surveyId;
	}

	// 설문 추천 결과 조회 요청 DTO
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@ToString
	public static class SurveyResultRequest {
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
	}

	// 설문 추천 결과 저장 요청 DTO
	@Getter
	@Setter
	public static class SurveySaveRequest {
		private String savedName;
	}
}
