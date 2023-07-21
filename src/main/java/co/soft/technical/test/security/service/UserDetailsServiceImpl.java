package co.soft.technical.test.security.service;
import co.soft.technical.test.security.model.MainUser;
import co.soft.technical.test.security.model.User;
import co.soft.technical.test.security.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // ~ Dependencies
    // ====================================================================
    private final UserRepository userRepository;

    // ~ Dependency Injection
    // ====================================================================
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return MainUser.build(user);
    }

}
