package Gift4U.Backend.security.auth.filter;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import Gift4U.Backend.apiPayload.code.status.ErrorStatus;
import Gift4U.Backend.apiPayload.exception.GeneralException;
import Gift4U.Backend.security.auth.manager.LogoutAccessTokenManager;
import Gift4U.Backend.security.auth.provider.JwtTokenProvider;
import Gift4U.Backend.security.auth.token.JwtAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


//요청에서 토큰을 추출해 유효성을 검증하고, Authentication을 SecurityContextHolder에 설정하는 클래스
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManager authenticationManager;
	private final LogoutAccessTokenManager logoutAccessTokenManager;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		log.debug("requestURI: {}, httpMethod: {}", request.getRequestURI(), request.getMethod());

		String accessToken = jwtTokenProvider.resolveToken(request);

		if (StringUtils.hasText(accessToken)) {
			String email = jwtTokenProvider.getSubject(accessToken);

			// 로그아웃된 액세스 토큰이 아닌 경우에만 JWT 인증 시도 및 설정
			if (logoutAccessTokenManager.findLogoutAccessToken(email)) {
				throw new GeneralException(ErrorStatus.LOGOUT_ACCESS_TOKEN);
			}

			JwtAuthenticationToken authRequest = new JwtAuthenticationToken(email, accessToken);
			Authentication authResult = authenticationManager.authenticate(authRequest);
			SecurityContextHolder.getContext().setAuthentication(authResult);
		}

		filterChain.doFilter(request, response);
	}
}

