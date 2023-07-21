package co.soft.technical.test.security.service;

import co.soft.technical.test.security.model.Role;
import co.soft.technical.test.security.model.enums.TypeRole;
import co.soft.technical.test.security.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements IRoleService{

    // ~ Dependencies
    // ====================================================================
    private final RoleRepository roleRepository;


    // ~ Dependency Injection
    // ====================================================================
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> getByTypeRole(TypeRole roleName) {
        return roleRepository.findByTypeRole(roleName);
    }

}
