package users.rishik.SpringAuthStarter.Services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import users.rishik.SpringAuthStarter.user.standard.UserDto;
import users.rishik.SpringAuthStarter.user.standard.User;
import users.rishik.SpringAuthStarter.user.standard.UserRepository;
import users.rishik.SpringAuthStarter.util.UserMapper;
import users.rishik.SpringAuthStarter.util.UserView;
import users.rishik.SpringAuthStarter.user.standard.UserService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private UserService userService;

    @Test
    void addUser() {

        UserDto userDto = new UserDto();
        userDto.setUsername("Tester");
        userDto.setEmail("test@example.com");
        userDto.setPassword("plain password");

        User mappedUser = new User();
        mappedUser.setEmail(userDto.getEmail());
        mappedUser.setUsername(userDto.getUsername());
        mappedUser.setPassword("encoded password");

        UserView expectedView = mock(UserView.class);
        when(expectedView.getEmail()).thenReturn(mappedUser.getEmail());

        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(false);

        when(userMapper.toUsers(userDto)).thenReturn(mappedUser);
        when(userRepository.save(mappedUser)).thenReturn(mappedUser);
        when(userRepository.findUserByEmail(mappedUser.getEmail())).thenReturn(expectedView);

        UserView result = userService.addUser(userDto);

        Assertions.assertEquals(userDto.getEmail(), result.getEmail());
    }

//    @Test
//    void verify() {
//        User user = new User();
//
//        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
//    }
}