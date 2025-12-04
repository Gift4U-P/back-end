package Gift4U.Backend.external;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import Gift4U.Backend.apiPayload.code.status.ErrorStatus;

import Gift4U.Backend.apiPayload.exception.GeneralException;
import Gift4U.Backend.external.dto.FastApiRequest;
import Gift4U.Backend.external.dto.FastApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;


@Component
@RequiredArgsConstructor
public class FastApiClient {

	private final RestTemplate restTemplate;

	@Value("${external.fastapi.home-present-url}")
	private String fastApiHomePresentUrl;

	@Value("${external.fastapi.survey-recommend-url}")
	private String fastApiSurveyRecommendUrl;

	@Value("${external.fastapi.keyword-recommend-url}")
	private String fastApiKeywordRecommendUrl;

	// 홈 선물 조회 API
	public FastApiResponse.HomePresentList getHomePresent() {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			// 바디 없이 POST 요청
			HttpEntity<Void> entity = new HttpEntity<>(headers);

			ResponseEntity<FastApiResponse.HomePresentList> response =
				restTemplate.exchange(
					fastApiHomePresentUrl,
					HttpMethod.GET,
					null,
					FastApiResponse.HomePresentList.class
				);

			return response.getBody();

		} catch (Exception e) {
			throw new GeneralException(ErrorStatus.FASTAPI_COMMUNICATION_ERROR);
		}
	}

	// 설문 추천 결과 조회 API
	public FastApiResponse.SearchSurveyList getSurveyRecommend(FastApiRequest.SurveyRequest request) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<FastApiRequest.SurveyRequest> entity = new HttpEntity<>(request, headers);

			ResponseEntity<FastApiResponse.SearchSurveyList> response =
				restTemplate.exchange(fastApiSurveyRecommendUrl, HttpMethod.POST, entity, FastApiResponse.SearchSurveyList.class);

			return response.getBody();

		} catch (Exception e) {
			throw new GeneralException(ErrorStatus.FASTAPI_COMMUNICATION_ERROR);
		}
	}

	// 키워드 추천 결과 조회 API
	public FastApiResponse.SearchKeywordList getKeywordRecommend(FastApiRequest.KeywordRequest request) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<FastApiRequest.KeywordRequest> entity = new HttpEntity<>(request, headers);

			ResponseEntity<FastApiResponse.SearchKeywordList> response =
				restTemplate.exchange(fastApiKeywordRecommendUrl, HttpMethod.POST, entity, FastApiResponse.SearchKeywordList.class);

			return response.getBody();

		} catch (Exception e) {
			throw new GeneralException(ErrorStatus.FASTAPI_COMMUNICATION_ERROR);
		}
	}
}
