package users.rishik.SpringAuthStarter.user.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import users.rishik.SpringAuthStarter.user.standard.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    MyUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (this.userRepository.existsByEmail(username))
            return new UserPrincipal(this.userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("No user exists with this email")));
        else return null;
    }
}
