package users.rishik.SpringAuthStarter.Services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import users.rishik.SpringAuthStarter.Dtos.LoginDto;
import users.rishik.SpringAuthStarter.Dtos.UserDto;
import users.rishik.SpringAuthStarter.Entities.User;
import users.rishik.SpringAuthStarter.UtilityClasses.UserMapper;
import users.rishik.SpringAuthStarter.jwt.JwtService;
import users.rishik.SpringAuthStarter.Exceptions.NotFoundException;
import users.rishik.SpringAuthStarter.Repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    UserService(UserRepository userRepository, UserMapper userMapper,
                AuthenticationManager authenticationManager, JwtService jwtService){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public User addUser(UserDto dto){
        if (userRepository.existsByEmail(dto.getEmail()))
            throw new IllegalArgumentException("Email already exists");
        dto.setPassword(encoder.encode(dto.getPassword()));
        User user = this.userMapper.toUsers(dto);
        return this.userRepository.save(user);
    }

    public User getUser(long id){
        return this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    public void deleteUser(long id){
        this.getUser(id);
        this.userRepository.deleteById(id);
    }

    public String verify(LoginDto user){
        if (!this.userRepository.existsByEmail(user.getEmail()))
            throw new NotFoundException("User email not found. Register to make a new account");
        Authentication auth = this.authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if (auth.isAuthenticated()){
            User existingUser = this.userRepository.findByEmail(user.getEmail());
            return this.jwtService.generateToken(existingUser);
        } else {
            return "Login Failed. Try again";
        }
    }
}
