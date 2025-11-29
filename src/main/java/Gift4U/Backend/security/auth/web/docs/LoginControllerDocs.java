package Gift4U.Backend.security.auth.web.docs;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import Gift4U.Backend.apiPayload.ApiResponse;
import Gift4U.Backend.security.auth.web.dto.AuthRequestDTO;
import Gift4U.Backend.security.auth.web.dto.AuthResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Tag(name = "Auth Login", description = "로그인 API")
public interface LoginControllerDocs {

	@Operation(
		summary = "로그인 API",
		description = "사용자의 이메일과 비밀번호로 로그인을 진행하는 API입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다.",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponseDTO.LoginResult.class))),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER4002", description = "비밀번호가 일치하지 않습니다."),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER4003", description = "해당 이메일을 가진 사용자가 존재하지 않습니다."),
		}
	)
	ResponseEntity<ApiResponse<AuthResponseDTO.LoginResult>> login(
		@RequestBody @Valid AuthRequestDTO.LoginRequestDTO loginRequest, HttpServletResponse response) throws IOException;
}
