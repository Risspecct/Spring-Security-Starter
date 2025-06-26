package users.rishik.SpringAuthStarter.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import users.rishik.SpringAuthStarter.Exceptions.ApiErrorResponse;
import users.rishik.SpringAuthStarter.Services.RateLimiterService;

import java.io.IOException;

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
        if (request.getRequestURI().equals("/login") && request.getMethod().equalsIgnoreCase("POST")) {
            String clientIp = request.getRemoteAddr();
            var bucket = rateLimiterService.resolveBucket(clientIp);

            if (!bucket.tryConsume(1)) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.TOO_MANY_REQUESTS.value(), "Rate Limit Exceeded", "Too many login attempts. Try again later.", request.getRequestURI());
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
