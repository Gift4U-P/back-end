package Gift4U.Backend.security.auth.manager;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import Gift4U.Backend.security.auth.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

// 로그아웃된 Access Token을 Redis에 저장하여 재사용을 막는 클래스

@Component
@RequiredArgsConstructor
public class LogoutAccessTokenManager {

	private static final String LOGOUT_ACCESS_TOKEN_PREFIX = "Logout:";
	private final StringRedisTemplate stringRedisTemplate;
	private final JwtTokenProvider jwtTokenProvider;

	public void saveLogoutAccessToken(String email, String accessToken) {
		long expirationMillis = jwtTokenProvider.getExpiration(accessToken);
		long nowMillis = System.currentTimeMillis();
		long durationMillis = expirationMillis - nowMillis;

		String key = LOGOUT_ACCESS_TOKEN_PREFIX + email;
		stringRedisTemplate.opsForValue().set(key, accessToken, Duration.ofMillis(durationMillis));
	}

	public boolean findLogoutAccessToken(String email) {
		String key = LOGOUT_ACCESS_TOKEN_PREFIX + email;
		return stringRedisTemplate.hasKey(key);
	}

	public String getLogoutAccessToken(String email) {
		String key = LOGOUT_ACCESS_TOKEN_PREFIX + email;
		return stringRedisTemplate.opsForValue().get(key);
	}

	public void deleteLogoutAccessToken(String email) {
		String key = LOGOUT_ACCESS_TOKEN_PREFIX + email;
		stringRedisTemplate.delete(key);
	}
}
