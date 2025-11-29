package Gift4U.Backend.security.auth.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthRequestDTO {

	@Getter
	@NoArgsConstructor
	public static class LoginRequestDTO {
		@NotNull
		@Schema(description = "사용자가 입력한 이메일", example = "user@example.com")
		private String email;
		@NotNull
		@Schema(description = "사용자가 입력한 비밀번호", example = "password123")
		private String password;
	}
}

