package Gift4U.Backend.user.web.dto;

import Gift4U.Backend.common.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequestDTO {

	// 회원가입 API
	@Getter
	@NoArgsConstructor
	public static class SignupRequestDTO {
		@NotBlank
		@Schema(description = "사용자가 입력한 이름", example = "김철수")
		@Pattern(
			regexp = "^[가-힣]+$",
			message = "한글만 입력할 수 있으며, 공백 없이 1자 이상 입력해주세요."
		)
		private String name;

		@NotNull
		@Schema(description = "사용자가 입력한 성별", example = "MALE", allowableValues = {"MALE", "FEMALE"})
		private Gender gender;

		@NotBlank
		@Email(message = "이메일 형식이 올바르지 않습니다.")
		@Schema(description = "사용자가 입력한 이메일", example = "user@example.com")
		private String email;

		@NotBlank
		@Schema(description = "사용자가 입력한 비밀번호", example = "asdf1234")
		@Pattern(
			regexp = "^[A-Za-z\\d@$!%*?&#]{8,20}$",
			message = "비밀번호는 영어 대소문자, 숫자, 특수문자(@$!%*?&#)만 허용되며, 공백 없이 8자 이상 20자 이하로 입력해주세요."
		)
		private String password;
	}
}