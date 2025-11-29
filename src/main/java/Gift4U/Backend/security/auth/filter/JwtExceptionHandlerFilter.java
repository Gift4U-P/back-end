package Gift4U.Backend.security.auth.filter;

import static Gift4U.Backend.apiPayload.ApiResponse.*;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import Gift4U.Backend.apiPayload.code.status.ErrorStatus;
import Gift4U.Backend.apiPayload.exception.GeneralException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


// Security Filter에서 발생하는 예외를 잡아 통일해둔 API 응답에 맞게 처리하는 클래스
@Slf4j
@Component
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		try {
			filterChain.doFilter(request, response);
		} catch (GeneralException e) {
			setErrorResponse(response, e.getErrorStatus(), e);
		} catch (UsernameNotFoundException e) {
			setErrorResponse(response, ErrorStatus.EMAIL_USER_NOT_MATCH, e);
		} catch (BadCredentialsException e) {
			setErrorResponse(response, ErrorStatus.PASSWORD_NOT_MATCH, e);
		} catch (Exception e) {
			log.error(e.getMessage(), e); // Exception Logging
			setErrorResponse(response, ErrorStatus._INTERNAL_SERVER_ERROR, e);
		}
	}
}
