package Gift4U.Backend.user.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Gift4U.Backend.apiPayload.code.status.ErrorStatus;
import Gift4U.Backend.apiPayload.exception.GeneralException;
import Gift4U.Backend.security.auth.manager.LogoutAccessTokenManager;
import Gift4U.Backend.security.auth.manager.RefreshTokenManager;
import Gift4U.Backend.security.auth.provider.JwtTokenProvider;
import Gift4U.Backend.security.auth.service.LoginService;
import Gift4U.Backend.security.auth.token.JwtAuthenticationToken;
import jakarta.servlet.http.HttpServletRequest;
import Gift4U.Backend.security.auth.web.dto.AuthResponseDTO;
import Gift4U.Backend.user.converter.UserConverter;
import Gift4U.Backend.user.domain.User;
import Gift4U.Backend.common.enums.Gender;
import Gift4U.Backend.user.repository.UserRepository;
import Gift4U.Backend.user.web.dto.UserRequestDTO;
import Gift4U.Backend.user.web.dto.UserResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final LoginService loginService;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserDetailsService userDetailsService;
	private final RefreshTokenManager refreshTokenManager;
	private final LogoutAccessTokenManager logoutAccessTokenManager;

	// 사용자 회원가입
	@Override
	public UserResponseDTO.SignupResult signup(UserRequestDTO.SignupRequestDTO request) {

		// RequestDTO 값 추출하기
		String name = request.getName();
		Gender gender = request.getGender();
		String email = request.getEmail();
		String password = request.getPassword();

		// email 중복 확인
		Optional<User> findUser = userRepository.findUserByEmail(email);
		if (findUser.isPresent()) {
			throw new GeneralException(ErrorStatus.EMAIL_DUPLICATE);
		}

		// 사용자 정보 엔티티 변환 및 DB 저장
		User newUser = UserConverter
			.toSignupUser(name, gender, email, passwordEncoder.encode(password));
		userRepository.save(newUser);

		// 사용자 회원가입 결과를 ResponseDTO로 응답
		return UserConverter.toSignupResult(newUser);
	}

	// 토큰 재발급 API - 리프레시 토큰으로 액세스 토큰과 리프레시 토큰 재발급
	@Override
	public AuthResponseDTO.LoginResult refresh(String reqRefreshToken, HttpServletResponse response) {

		if (reqRefreshToken == null || reqRefreshToken.isBlank()) {
			throw new GeneralException(ErrorStatus.REFRESH_TOKEN_NOT_FOUND);
		}

		// 리프레시 토큰에서 Subject 추출
		String email = jwtTokenProvider.getSubject(reqRefreshToken);
		UserDetails userDetails = userDetailsService.loadUserByUsername(email);

		// 토큰 생성 및 DTO에 담기
		JwtAuthenticationToken request = new JwtAuthenticationToken(
			userDetails, null, userDetails.getAuthorities());
		return loginService.generateAuthResponse(email, request, response);
	}

	// 로그아웃 API - 액세스 토큰과 리프레시 토큰 블랙리스트화
	@Override
	public String logout(HttpServletRequest request) {

		// 요청에서 액세스 토큰 추출 및 유효성 검증
		String accessToken = jwtTokenProvider.resolveToken(request);
		jwtTokenProvider.validateToken(accessToken);

		// 토큰에서 email 추출 및 사용자 검증
		String email = jwtTokenProvider.getSubject(accessToken);
		userDetailsService.loadUserByUsername(email);

		// 액세스 토큰 블랙리스트화
		logoutAccessTokenManager.saveLogoutAccessToken(email, accessToken);

		// 리프레시 토큰 삭제
		if (refreshTokenManager.findRefreshToken(email)) {
			refreshTokenManager.deleteRefreshToken(email);
		}

		return email;
	}

	// 프로필 조회 API
	@Override
	public UserResponseDTO.ProfileResult profile(Long userId) {
		// 사용자 조회
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.EMAIL_USER_NOT_MATCH));

		return UserConverter.toMyPageProfileResponse(user);
	}
}
