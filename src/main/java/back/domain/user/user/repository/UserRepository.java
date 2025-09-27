package back.domain.user.user.repository;

import back.domain.user.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {

    Optional<User> findByUsername(String username);

    User save(User user);
}
