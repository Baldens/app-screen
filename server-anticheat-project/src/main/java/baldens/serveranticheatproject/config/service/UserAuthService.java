package baldens.serveranticheatproject.config.service;

import baldens.serveranticheatproject.repository.http.RoleRepository;
import baldens.serveranticheatproject.repository.http.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserAuthService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserAuthService(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findByLogin(login)
                .map(user -> new User(
                        user.getLogin(),
                        user.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority(roleRepository.findById(Long.parseLong(user.getId_role().toString())).stream().findFirst().get().getName_role()))
                )).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }
}
