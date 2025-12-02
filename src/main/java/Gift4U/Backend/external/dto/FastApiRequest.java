package Gift4U.Backend.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class FastApiRequest {

	// 설문 조회용 요청 DTO
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class SurveyRequest {
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

	// 키워드 조회용 요청 DTO
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class KeywordRequest {
		private String age;
		private String gender;
		private String relationship;
		private String situation;
	}
}
