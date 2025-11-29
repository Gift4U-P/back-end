package Gift4U.Backend.user.web.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserResponseDTO {

	// 회원가입 API
	@Builder
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SignupResult {
		private Long userId;
		private String name;
		private LocalDateTime createdAt;

	}
}
