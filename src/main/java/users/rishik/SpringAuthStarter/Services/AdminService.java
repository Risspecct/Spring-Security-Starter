package users.rishik.SpringAuthStarter.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import users.rishik.SpringAuthStarter.Entities.Roles;
import users.rishik.SpringAuthStarter.Entities.User;
import users.rishik.SpringAuthStarter.Exceptions.NotFoundException;
import users.rishik.SpringAuthStarter.Repositories.UserRepository;
import users.rishik.SpringAuthStarter.UtilityClasses.UserView;

import java.util.List;

@Slf4j
@Service
public class AdminService {
    private final UserRepository userRepository;

    AdminService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<UserView> getAllUsers(){
        List<UserView> users = this.userRepository.findAllBy();
        if (users.isEmpty()) throw new NotFoundException("No users found in the database");
        else return users;
    }

    public void deleteUser(long id){
        log.info("Attempting to delete user with id: {}", id);
        if (!this.userRepository.existsById(id)) throw new NotFoundException("User with id: " + id + " not found");
        this.userRepository.deleteById(id);
        log.info("Deleted user with id: {}", id);
    }

    public void updateRole(long userId, Roles role){
        User existingUser = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        log.info("Updating role of user with id: {}", userId);
        existingUser.setRole(role);
        this.userRepository.save(existingUser);
    }
}
