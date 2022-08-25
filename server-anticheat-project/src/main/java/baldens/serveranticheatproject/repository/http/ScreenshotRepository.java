package baldens.serveranticheatproject.repository.http;

import baldens.serveranticheatproject.model.http.Screenshot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreenshotRepository extends JpaRepository<Screenshot, Long> {
//    public List<Screenshot> findByIdUser(Integer id_user);
}