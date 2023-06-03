package co.soft.technical.test.security.config;

import co.soft.technical.test.security.model.Role;
import co.soft.technical.test.security.model.enums.TypeRole;
import co.soft.technical.test.security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;

@Configuration
@Order(1)
public class RolesInitializer {

    private final RoleRepository roleRepository;

    @Autowired
    public RolesInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @PostConstruct
    public void init() {
        Role adminRole = new Role(TypeRole.ROLE_ADMIN);
        Role userRole = new Role(TypeRole.ROLE_USER);

        if (roleRepository.findByTypeRole(TypeRole.ROLE_ADMIN).isEmpty()) {
            roleRepository.save(adminRole);
        }

        if (roleRepository.findByTypeRole(TypeRole.ROLE_USER).isEmpty()) {
            roleRepository.save(userRole);
        }
    }
}
