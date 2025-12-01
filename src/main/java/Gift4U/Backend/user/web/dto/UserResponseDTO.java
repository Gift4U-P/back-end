package Gift4U.Backend.user.web.dto;

import java.time.LocalDateTime;

import Gift4U.Backend.common.enums.Gender;
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

	// 프로필 조회 API
	@Builder
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ProfileResult {
		private String name;
		private Gender gender;
		private String email;
	}
}
