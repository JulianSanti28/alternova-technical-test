package co.soft.technical.test.security.service;


import co.soft.technical.test.security.dto.UserDTO;
import co.soft.technical.test.security.model.Role;
import co.soft.technical.test.security.model.User;
import co.soft.technical.test.security.model.enums.TypeRole;
import co.soft.technical.test.security.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements IUserService{

    // ~ Dependencies
    // ====================================================================
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IRoleService roleService;

    // ~ Dependency Injection
    // ====================================================================
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, IRoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }


    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void save(UserDTO userDTO) {
        User user = new User(userDTO.getName(), userDTO.getLastName(),
                userDTO.getUsername(), userDTO.getEmail(),
                passwordEncoder.encode(userDTO.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getByTypeRole(TypeRole.ROLE_USER).orElseThrow());

        if(userDTO.getRoles().contains("admin")){
            roles.add(roleService.getByTypeRole(TypeRole.ROLE_ADMIN).orElseThrow());
        }
        user.setRoles(roles);
        userRepository.save(user);
    }

}
