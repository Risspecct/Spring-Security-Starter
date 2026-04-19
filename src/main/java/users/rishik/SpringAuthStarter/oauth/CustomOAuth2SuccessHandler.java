package users.rishik.SpringAuthStarter.oauth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import users.rishik.SpringAuthStarter.jwt.refresh_tokens.RefreshToken;
import users.rishik.SpringAuthStarter.jwt.refresh_tokens.RefreshTokenService;
import users.rishik.SpringAuthStarter.user.standard.User;
import users.rishik.SpringAuthStarter.user.standard.UserRepository;
import users.rishik.SpringAuthStarter.jwt.security.JwtService;

import java.io.IOException;

@Slf4j
@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @Value("${app.oauth2.success-redirect-uri:http://localhost:8080/oauth-success.html}")
    private String oauthSuccessRedirectUri;

    CustomOAuth2SuccessHandler(UserRepository userRepository, JwtService jwtService,
                               RefreshTokenService refreshTokenService){
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
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

        String token = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getEmail());

        String redirectUrl = UriComponentsBuilder.fromUriString(oauthSuccessRedirectUri)
                .queryParam("accessToken", token)
                .queryParam("refreshToken", refreshToken.getToken())
                .build(true)
                .toUriString();

        response.sendRedirect(redirectUrl);
    }
}
