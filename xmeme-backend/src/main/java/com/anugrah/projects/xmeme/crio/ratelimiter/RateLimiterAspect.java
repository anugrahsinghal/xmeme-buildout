package com.anugrah.projects.xmeme.crio.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RateLimiterAspect {

	private final Map<String, RateLimiter> limiterMap = new ConcurrentHashMap<>();

	public void rateLimit(RateLimit limit, JoinPoint joinPoint) {
		String key = getOrCreateKey(limit, joinPoint);
		RateLimiter limiter = limiterMap.computeIfAbsent(key, (name -> RateLimiter.create(limit.value())));
		limiter.acquire();
	}

	private String getOrCreateKey(RateLimit limit, JoinPoint joinPoint) {
		if (!limit.key().isEmpty()) {
			return limit.key();
		}
		return JoinPointToStringHelper.toString(joinPoint);
	}

	private static class JoinPointToStringHelper {
		public static String toString(JoinPoint joinPoint) {
			return joinPoint.toLongString();
		}
	}
}
