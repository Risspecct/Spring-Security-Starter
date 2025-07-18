package users.rishik.SpringAuthStarter.OAuth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import users.rishik.SpringAuthStarter.user.standard.User;
import users.rishik.SpringAuthStarter.user.standard.UserRepository;
import users.rishik.SpringAuthStarter.jwt.security.JwtService;

import java.io.IOException;

@Slf4j
@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    CustomOAuth2SuccessHandler(UserRepository userRepository, JwtService jwtService){
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("OAuth2 success handler triggered, principal: {}", authentication.getPrincipal().getClass().getName());

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");

        // Load user from DB
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        log.info("Generating jwt token for OAUth user");

        // Generate JWT using existing logic
        String token = jwtService.generateToken(user);

        // Send token in response (you can also set cookie or redirect with token)
        response.setContentType("application/json");
        response.getWriter().write("{\"token\": \"" + token + "\"}");
    }
}
