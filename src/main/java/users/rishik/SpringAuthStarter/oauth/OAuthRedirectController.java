package users.rishik.SpringAuthStarter.oauth;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class OAuthRedirectController {

    @GetMapping("/login/{provider}")
    @Operation(summary = "Redirect to OAuth2 login", description = "provider = google or github")
    public String getOAuthLoginUrl(@PathVariable String provider) {
        // Construct the URL to paste in a browser
        return "https://spring-security-starter.onrender.com/oauth2/authorization/" + provider;
    }
}