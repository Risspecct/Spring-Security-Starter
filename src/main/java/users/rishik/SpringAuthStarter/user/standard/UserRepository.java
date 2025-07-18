package users.rishik.SpringAuthStarter.user.standard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import users.rishik.SpringAuthStarter.util.UserView;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<UserView> findAllBy();

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    UserView findUserByEmail(String email);

    Optional<UserView> findUserById(long id);

}


