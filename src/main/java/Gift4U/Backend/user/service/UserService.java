package Gift4U.Backend.user.service;

import Gift4U.Backend.security.auth.web.dto.AuthResponseDTO;
import Gift4U.Backend.user.web.dto.UserRequestDTO;
import Gift4U.Backend.user.web.dto.UserResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

	// 회원가입 API
	UserResponseDTO.SignupResult signup(UserRequestDTO.SignupRequestDTO request);

	// 토큰 재발급 API
	AuthResponseDTO.LoginResult refresh(String refreshToken, HttpServletResponse response);

	// 로그아웃 API
	String logout(HttpServletRequest request);

	// 프로필 조회 API
	UserResponseDTO.ProfileResult profile(Long userId);
}
