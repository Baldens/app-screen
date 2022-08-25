package baldens.serveranticheatproject.route.api;

import baldens.serveranticheatproject.model.http.Users;
import baldens.serveranticheatproject.repository.http.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("api")
public class RegistrationApi {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @PostMapping("/user-registration")
    public ResponseEntity registration(@RequestBody Users user) {
        try{

            if(userRepository.findByLogin(user.getLogin()).stream().findAny().isEmpty()){
                Users userSave = new Users();
                userSave.setName(user.getName());
                userSave.setLogin(user.getLogin());
                userSave.setPassword(passwordEncoder.encode(user.getPassword()));
                userSave.setId_role(1);

//                userRepository.save(userSave);
                return ResponseEntity.ok("Ok");
            }else{
                return ResponseEntity.badRequest().body("Error");
            }

        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
