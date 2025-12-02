package Gift4U.Backend.user.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Gift4U.Backend.apiPayload.ApiResponse;
import Gift4U.Backend.apiPayload.code.status.SuccessStatus;
import Gift4U.Backend.security.auth.userDetails.CustomUserDetails;
import Gift4U.Backend.security.auth.web.dto.AuthResponseDTO;
import Gift4U.Backend.user.service.UserService;
import Gift4U.Backend.user.web.docs.UserControllerDocs;
import Gift4U.Backend.user.web.dto.UserRequestDTO;
import Gift4U.Backend.user.web.dto.UserResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController implements UserControllerDocs {

	private final UserService userService;

	// 회원가입 API
	@PostMapping("/signup")
	public ResponseEntity<ApiResponse<UserResponseDTO.SignupResult>> signup(
		@RequestBody @Valid UserRequestDTO.SignupRequestDTO request) {
		UserResponseDTO.SignupResult result = userService.signup(request);
		return new ResponseEntity<>(ApiResponse.of(SuccessStatus._CREATED_ID, result), HttpStatus.CREATED);
	}

	// 토큰 재발급 API
	@PostMapping("/refresh")
	public ResponseEntity<ApiResponse<AuthResponseDTO.LoginResult>> refresh(
		@RequestHeader(name = "Refresh-Token") String refreshToken, HttpServletResponse response) {
		AuthResponseDTO.LoginResult result = userService.refresh(refreshToken, response);
		return ResponseEntity.ok().body(ApiResponse.onSuccess(result));
	}

	// 로그아웃 API
	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<Object>> logout(HttpServletRequest request) {
		userService.logout(request);
		return ResponseEntity.ok().body(ApiResponse.onSuccess(null));
	}

	// 프로필 조회 API
	@GetMapping("/profile")
	public ResponseEntity<ApiResponse<UserResponseDTO.ProfileResult>> profile(
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		Long userId = userDetails.getUserId();
		UserResponseDTO.ProfileResult result = userService.profile(userId);
		return new ResponseEntity<>(ApiResponse.of(SuccessStatus._CREATED_ID, result), HttpStatus.CREATED);
	}

	// 홈 선물 조회 API
	@GetMapping("/present/list")
	public ResponseEntity<ApiResponse<UserResponseDTO.HomePresentList>> homePresent() {
		UserResponseDTO.HomePresentList result = userService.homePresent();
		return new ResponseEntity<>(ApiResponse.of(SuccessStatus._OK, result), HttpStatus.CREATED);
	}
}









