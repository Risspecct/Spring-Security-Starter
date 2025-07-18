package users.rishik.SpringAuthStarter.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import users.rishik.SpringAuthStarter.user.roles.Roles;
import users.rishik.SpringAuthStarter.user.standard.User;
import users.rishik.SpringAuthStarter.user.standard.UserRepository;

import java.util.Optional;

@Slf4j
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    CustomOAuth2UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        log.info("CustomOAuth2UserService triggered");
        String email = null;
        String name = null;
        String providerId = null;

        String provider = userRequest.getClientRegistration().getRegistrationId();
        if ("google".equals(provider)) {
            email = oAuth2User.getAttribute("email");
            name = oAuth2User.getAttribute("name");
            providerId = oAuth2User.getAttribute("sub");
        } else if ("github".equals(provider)) {
            email = oAuth2User.getAttribute("email"); // might be null
            name = oAuth2User.getAttribute("login");
            providerId = oAuth2User.getAttribute("id").toString();
        }

        Optional<User> existingUser = userRepository.findByEmail(email);

        User user = existingUser.orElseGet(User::new);
        user.setProvider(provider);
        user.setProviderId(providerId);
        user.setEmail(email);
        user.setUsername(name);
        user.setRole(Roles.USER); // Default

        log.info("Saving user details in the Database");

        this.userRepository.save(user);
        return new CustomOAuth2User(oAuth2User, user);
    }
}
