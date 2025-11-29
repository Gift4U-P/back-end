package Gift4U.Backend.user.converter;

import Gift4U.Backend.user.domain.User;
import Gift4U.Backend.common.enums.Gender;
import Gift4U.Backend.user.web.dto.UserResponseDTO;

public class UserConverter {

	// 사용자 회원가입을 위한 엔티티로 변환
	public static User toSignupUser(String name, Gender gender, String email, String password) {
		return User.builder()
			.name(name)
			.gender(gender)
			.email(email)
			.password(password)
			.build();
	}

	// 사용자를 회원가입 결과 DTO 반환
	public static UserResponseDTO.SignupResult toSignupResult(User user) {
		return UserResponseDTO.SignupResult.builder()
			.userId(user.getId())
			.name(user.getName())
			.createdAt(user.getCreatedAt())
			.build();
	}
}
