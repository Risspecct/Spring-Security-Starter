package users.rishik.SpringAuthStarter.Repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import users.rishik.SpringAuthStarter.Entities.RefreshToken;
import users.rishik.SpringAuthStarter.Entities.User;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    RefreshToken findByUser(User user);
}
