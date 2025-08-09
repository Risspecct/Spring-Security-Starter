package users.rishik.SpringAuthStarter.util;

import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import users.rishik.SpringAuthStarter.user.roles.Roles;
import users.rishik.SpringAuthStarter.user.standard.User;
import users.rishik.SpringAuthStarter.user.standard.UserRepository;

@SuppressWarnings("unused")
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    private final Environment env;

    public DataInitializer(UserRepository userRepository, Environment env) {
        this.userRepository = userRepository;
        this.env = env;
    }

    @Override
    public void run(String... args) {
        createDefaultAdmin();
        createDefaultUser();
    }

    private void createDefaultAdmin() {
        String adminEmail = env.getProperty("DEFAULT_ADMIN_EMAIL");
        String adminPassword = env.getProperty("DEFAULT_ADMIN_PASSWORD");

        if (adminEmail != null && adminPassword != null && !userRepository.existsByEmail(adminEmail)) {
            User admin = new User();
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRole(Roles.ADMIN);
            admin.setUsername("Admin");
            userRepository.save(admin);
            System.out.println("Default admin created: " + adminEmail);
        } else {
            System.out.println("ℹ No default admin created (missing env vars or already exists)");
        }
    }

    private void createDefaultUser() {
        String userEmail = env.getProperty("DEFAULT_USER_EMAIL");
        String userPassword = env.getProperty("DEFAULT_USER_PASSWORD");

        if (userEmail != null && userPassword != null && !userRepository.existsByEmail(userEmail)) {
            User user = new User();
            user.setEmail(userEmail);
            user.setPassword(passwordEncoder.encode(userPassword));
            user.setRole(Roles.USER);
            user.setUsername("User");
            userRepository.save(user);
            System.out.println("Default user created: " + userEmail);
        } else {
            System.out.println("ℹ No default user created (missing env vars or already exists)");
        }
    }
}