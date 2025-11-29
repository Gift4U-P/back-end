package Gift4U.Backend.security.auth.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthResponseDTO {

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class LoginResult {
		private String refreshToken;
		private Long userId;
		private String name;
	}
}
