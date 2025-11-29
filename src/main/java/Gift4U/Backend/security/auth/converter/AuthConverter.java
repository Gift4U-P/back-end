package Gift4U.Backend.security.auth.converter;

import Gift4U.Backend.security.auth.web.dto.AuthResponseDTO;

public class AuthConverter {

	public static AuthResponseDTO.LoginResult toLoginResult(String refreshToken, Long userId, String name) {
		return AuthResponseDTO.LoginResult.builder()
			.refreshToken(refreshToken)
			.userId(userId)
			.name(name)
			.build();
	}
}
