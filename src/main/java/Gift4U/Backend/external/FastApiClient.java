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
import Gift4U.Backend.external.dto.FastApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;


@Component
@RequiredArgsConstructor
public class FastApiClient {

	private final RestTemplate restTemplate;

	@Value("${external.fastapi.home-present-url}")
	private String fastApiHomePresentUrl;

	public FastApiResponse.HomePresentList getHomePresent() {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			// 바디 없이 POST 요청
			HttpEntity<Void> entity = new HttpEntity<>(headers);

			ResponseEntity<FastApiResponse.HomePresentList> response =
				restTemplate.exchange(
					fastApiHomePresentUrl,
					HttpMethod.POST,
					entity,
					FastApiResponse.HomePresentList.class
				);

			return response.getBody();

		} catch (Exception e) {
			throw new GeneralException(ErrorStatus.FASTAPI_COMMUNICATION_ERROR);
		}
	}
}
