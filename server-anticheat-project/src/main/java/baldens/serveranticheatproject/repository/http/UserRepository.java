package baldens.serveranticheatproject.repository.http;

import baldens.serveranticheatproject.model.http.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByLoginAndPassword(String login, String password);
    Optional<Users> findByLogin(String login);
}
