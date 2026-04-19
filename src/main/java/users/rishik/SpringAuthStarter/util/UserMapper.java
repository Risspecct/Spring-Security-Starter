package users.rishik.SpringAuthStarter.util;

import org.springframework.stereotype.Component;
import users.rishik.SpringAuthStarter.user.standard.UserDto;
import users.rishik.SpringAuthStarter.user.roles.Roles;
import users.rishik.SpringAuthStarter.user.standard.User;

@Component
public class UserMapper {
    public User toUsers(UserDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(Roles.USER);
        return user;
    }
}
