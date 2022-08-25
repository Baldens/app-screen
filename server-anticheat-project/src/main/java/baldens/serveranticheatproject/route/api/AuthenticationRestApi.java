package baldens.serveranticheatproject.route.api;

import baldens.serveranticheatproject.config.jwt.JwtTokenUtil;
import baldens.serveranticheatproject.dto.JwtRequestAuth;
import baldens.serveranticheatproject.dto.JwtResponse;
import baldens.serveranticheatproject.model.http.Users;
import baldens.serveranticheatproject.repository.http.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class AuthenticationRestApi {
    /**
     * Регистрация
     * */

    @Autowired
    private UserRepository userRepository;


    /**
     * Авторизация
     * */

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    public AuthenticationRestApi(@Qualifier("userServiceAuth") UserDetailsService userDetailsService,
                               JwtTokenUtil jwtTokenUtil,
                               AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequestAuth authRequest) throws Exception{
        try {
            authenticate(authRequest.getLogin(), authRequest.getPassword());
        }
        catch (BadCredentialsException ex){
            throw new Exception("Incorrect username or password", ex);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getLogin());
        String token = jwtTokenUtil.generateToken(userDetails);
        Users user = userRepository.findByLogin(authRequest.getLogin()).stream().findFirst().get();
        return ResponseEntity.ok(new JwtResponse(user.getId(), token));
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
