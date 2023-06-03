package co.soft.technical.test.security.repository;

import co.soft.technical.test.security.model.Role;
import co.soft.technical.test.security.model.enums.TypeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByTypeRole(TypeRole roleName);
}
