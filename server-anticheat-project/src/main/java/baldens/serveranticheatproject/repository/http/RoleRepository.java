package baldens.serveranticheatproject.repository.http;

import baldens.serveranticheatproject.model.http.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role, Long> {
}
