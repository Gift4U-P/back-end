package Gift4U.Backend.security.auth.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Gift4U.Backend.apiPayload.code.status.ErrorStatus;
import Gift4U.Backend.apiPayload.exception.GeneralException;
import Gift4U.Backend.security.auth.converter.AuthConverter;
import Gift4U.Backend.security.auth.manager.LogoutAccessTokenManager;
import Gift4U.Backend.security.auth.manager.RefreshTokenManager;
import Gift4U.Backend.security.auth.provider.CustomLoginAuthenticationProvider;
import Gift4U.Backend.security.auth.provider.JwtTokenProvider;
import Gift4U.Backend.security.auth.userDetails.CustomUserDetails;
import Gift4U.Backend.security.auth.web.dto.AuthRequestDTO;
import Gift4U.Backend.security.auth.web.dto.AuthResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

	private final CustomLoginAuthenticationProvider customLoginAuthenticationProvider;
	private final JwtTokenProvider jwtTokenProvider;
	private final RefreshTokenManager refreshTokenManager;
	private final LogoutAccessTokenManager logoutAccessTokenManager;

	@Override
	public AuthResponseDTO.LoginResult login(AuthRequestDTO.LoginRequestDTO requestDTO, HttpServletResponse response) {

		// 인증 시도 및 Authentication 획득
		UsernamePasswordAuthenticationToken loginRequest = UsernamePasswordAuthenticationToken
			.unauthenticated(requestDTO.getEmail(), requestDTO.getPassword());
		Authentication authResult;
		try {
			authResult = customLoginAuthenticationProvider.authenticate(loginRequest);
		} catch (BadCredentialsException failed) {
			throw new GeneralException(ErrorStatus.PASSWORD_NOT_MATCH);
		} catch (UsernameNotFoundException failed) {
			throw new GeneralException(ErrorStatus.EMAIL_USER_NOT_MATCH);
		} catch (Exception failed) {
			throw new GeneralException(ErrorStatus.LOGIN_UNKNOWN_ERROR);
		}

		// Authentication에서 principal String 추출
		String email = authResult.getName();

		// 사용자의 로그아웃 액세스 토큰이 존재하는 경우 삭제
		if (logoutAccessTokenManager.findLogoutAccessToken(email)) {
			logoutAccessTokenManager.deleteLogoutAccessToken(email);
		}

		// SecurityContextHolder에 인증 설정
		SecurityContextHolder.getContext().setAuthentication(authResult);

		return generateAuthResponse(email, authResult, response);
	}

	@Override
	public AuthResponseDTO.LoginResult generateAuthResponse(String email,
		Authentication request, HttpServletResponse response) {

		// Authentication에서 userId 추출
		Long userId = ((CustomUserDetails)request.getPrincipal()).getUserId();
		String name = ((CustomUserDetails)request.getPrincipal()).getName();

		// 토큰 생성 및 DTO에 담기
		String accessToken = jwtTokenProvider.createAccessToken(request);
		String refreshToken = jwtTokenProvider.createRefreshToken(request);

		// 새로 발급한 리프레시 토큰을 Redis에 저장 - 덮어씌우기
		refreshTokenManager.saveRefreshToken(email, refreshToken);

		// 응답 헤더 작성
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		response.setHeader("Authorization", "Bearer " + accessToken);

		return AuthConverter.toLoginResult(refreshToken, userId, name);
	}
}

