package co.soft.technical.test.security.service;

import co.soft.technical.test.security.dto.UserDTO;
import co.soft.technical.test.security.model.User;

import java.util.Optional;

public interface IUserService {
    public Optional<User> getByUsername(String username);
    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);
    public void save(UserDTO userDTO);
}
