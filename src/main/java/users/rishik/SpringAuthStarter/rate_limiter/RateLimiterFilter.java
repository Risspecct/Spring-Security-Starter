package users.rishik.SpringAuthStarter.rate_limiter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import users.rishik.SpringAuthStarter.exceptions.ApiErrorResponse;

import java.io.IOException;

@Slf4j
@Component
public class RateLimiterFilter extends OncePerRequestFilter {
    @Autowired
    private final RateLimiterService rateLimiterService;
    @Autowired
    private final ObjectMapper objectMapper;

    public RateLimiterFilter(RateLimiterService rateLimiterService, ObjectMapper objectMapper){
        this.rateLimiterService = rateLimiterService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isRateLimitedLoginRequest(request)) {
            String clientIp = request.getRemoteAddr();
            ConsumptionProbe probe = rateLimiterService.consumeLoginAttempt(clientIp);
            attachRateLimitHeaders(response, probe.getRemainingTokens());

            if (!probe.isConsumed()) {
                log.warn("Rate limit exceeded for IP: {}", request.getRemoteAddr());
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.setHeader("Retry-After", String.valueOf(Math.max(1, probe.getNanosToWaitForRefill() / 1_000_000_000)));

                ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.TOO_MANY_REQUESTS.value(), "Rate Limit Exceeded", "Too many login attempts. Try again later.", request.getRequestURI());
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isRateLimitedLoginRequest(HttpServletRequest request) {
        if (!request.getMethod().equalsIgnoreCase("POST")) {
            return false;
        }
        String uri = request.getRequestURI();
        return uri.equals("/login") || uri.equals("/api/auth/login");
    }

    private void attachRateLimitHeaders(HttpServletResponse response, long remaining) {
        response.setHeader("X-RateLimit-Limit", String.valueOf(rateLimiterService.getLoginLimitCapacity()));
        response.setHeader("X-RateLimit-Remaining", String.valueOf(Math.max(remaining, 0)));
        response.setHeader("X-RateLimit-Used", String.valueOf(rateLimiterService.getLoginLimitCapacity() - Math.max(remaining, 0)));
    }
}
