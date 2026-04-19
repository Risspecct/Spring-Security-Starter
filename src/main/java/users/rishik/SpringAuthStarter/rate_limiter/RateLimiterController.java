package users.rishik.SpringAuthStarter.rate_limiter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class RateLimiterController {
    private final RateLimiterService rateLimiterService;

    RateLimiterController(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @GetMapping({"/rate-limit/login-status", "/api/rate-limit/login-status"})
    public ResponseEntity<RateLimitStatusDto> getLoginRateLimitStatus(HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        long limit = rateLimiterService.getLoginLimitCapacity();
        long remaining = rateLimiterService.getRemainingAttempts(clientIp);
        long used = rateLimiterService.getUsedAttempts(clientIp);

        return ResponseEntity.ok(
                RateLimitStatusDto.builder()
                        .endpoint("POST /login, POST /api/auth/login")
                        .limit(limit)
                        .used(used)
                        .remaining(remaining)
                        .build()
        );
    }
}
