package Gift4U.Backend.survey.web.dto;

import lombok.Getter;
import lombok.Setter;

public class SurveyRequestDTO {

	// 설문 결과 상세 조회 API
	@Getter
	@Setter
	public static class SurveyRequest {
		private Long surveyId;
	}
}
