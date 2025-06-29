package users.rishik.SpringAuthStarter.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import users.rishik.SpringAuthStarter.Entities.User;
import users.rishik.SpringAuthStarter.UtilityClasses.UserView;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<UserView> findAllBy();

    Boolean existsByEmail(String email);

    User findByEmail(String email);

    UserView findUserByEmail(String email);
}
