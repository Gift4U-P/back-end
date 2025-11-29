package Gift4U.Backend.security.auth.web.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Gift4U.Backend.apiPayload.ApiResponse;
import Gift4U.Backend.security.auth.service.LoginService;
import Gift4U.Backend.security.auth.web.docs.LoginControllerDocs;
import Gift4U.Backend.security.auth.web.dto.AuthRequestDTO;
import Gift4U.Backend.security.auth.web.dto.AuthResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class LoginController implements LoginControllerDocs {

	private final LoginService loginService;

	// 로그인 API
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<AuthResponseDTO.LoginResult>> login(
		@RequestBody @Valid AuthRequestDTO.LoginRequestDTO loginRequest, HttpServletResponse response) throws IOException {
		AuthResponseDTO.LoginResult loginResult = loginService.login(loginRequest, response);
		return ResponseEntity.ok().body(ApiResponse.onSuccess(loginResult));
	}
}
