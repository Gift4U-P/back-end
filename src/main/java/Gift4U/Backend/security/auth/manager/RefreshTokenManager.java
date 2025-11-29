package Gift4U.Backend.security.auth.manager;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import Gift4U.Backend.security.auth.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

// 리프레시 토큰을 Redis에 저장, 수정, 삭제를 담당하는 클래스

@Component
@RequiredArgsConstructor
public class RefreshTokenManager {

	private static final String REFRESH_TOKEN_PREFIX = "RT:";
	private final StringRedisTemplate stringRedisTemplate;
	private final JwtTokenProvider jwtTokenProvider;

	public void saveRefreshToken(String email, String refreshToken) {
		long expirationMillis = jwtTokenProvider.getExpiration(refreshToken);
		long nowMillis = System.currentTimeMillis();
		long durationMillis = expirationMillis - nowMillis;

		String key = REFRESH_TOKEN_PREFIX + email;
		stringRedisTemplate.opsForValue().set(key, refreshToken, Duration.ofMillis(durationMillis));
	}

	public boolean findRefreshToken(String email) {
		String key = REFRESH_TOKEN_PREFIX + email;
		return stringRedisTemplate.hasKey(key);
	}

	public String getRefreshToken(String email) {
		String key = REFRESH_TOKEN_PREFIX + email;
		return stringRedisTemplate.opsForValue().get(key);
	}

	public void deleteRefreshToken(String email) {
		String key = REFRESH_TOKEN_PREFIX + email;
		stringRedisTemplate.delete(key);
	}
}
