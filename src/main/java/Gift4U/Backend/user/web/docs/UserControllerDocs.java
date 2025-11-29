package Gift4U.Backend.user.web.docs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import Gift4U.Backend.apiPayload.ApiResponse;
import Gift4U.Backend.security.auth.web.dto.AuthResponseDTO;
import Gift4U.Backend.user.web.dto.UserRequestDTO;
import Gift4U.Backend.user.web.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Tag(name = "User", description = "사용자 CRUD API")
public interface UserControllerDocs {

	@Operation(
		summary = "회원가입 API",
		description = "사용자의 이름, 성별, 이메일, 비밀번호를 입력받아 회원가입하는 API입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "회원가입에 성공했습니다.",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.SignupResult.class))),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER4001", description = "이미 사용된 이메일입니다."),
		}
	)
	ResponseEntity<ApiResponse<UserResponseDTO.SignupResult>> signup(
		@RequestBody @Valid UserRequestDTO.SignupRequestDTO request);

	@Operation(
		summary = "토큰 재발급 API",
		description = "헤더에 입력한 Refresh-Token으로 새로운 액세스 토큰과 리프레시 토큰을 발급하는 API입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다."),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN4002", description = "해당 리프레시 토큰이 존재하지 않습니다.")
		}
	)
	ResponseEntity<ApiResponse<AuthResponseDTO.LoginResult>> refresh(
		@RequestHeader(name = "Refresh-Token") String refreshToken, HttpServletResponse response);

	@Operation(
		summary = "로그아웃 API",
		description = "사용자의 액세스 토큰과 리프레시 토큰을 블랙리스트화(로그아웃)하는 API입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다."),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER4003", description = "해당 아이디를 가진 사용자가 존재하지 않습니다."),
		}
	)
	ResponseEntity<ApiResponse<Object>> logout(HttpServletRequest request);
}
