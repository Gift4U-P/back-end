package Gift4U.Backend.security.auth.service;

import java.io.IOException;

import org.springframework.security.core.Authentication;

import Gift4U.Backend.security.auth.web.dto.AuthRequestDTO;
import Gift4U.Backend.security.auth.web.dto.AuthResponseDTO;
import jakarta.servlet.http.HttpServletResponse;

public interface LoginService {

	public AuthResponseDTO.LoginResult login(AuthRequestDTO.LoginRequestDTO request, HttpServletResponse response) throws
		IOException;

	AuthResponseDTO.LoginResult generateAuthResponse(String email,
		Authentication request, HttpServletResponse response);
}
