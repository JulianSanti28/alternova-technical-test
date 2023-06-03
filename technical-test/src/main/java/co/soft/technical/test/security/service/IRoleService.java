package co.soft.technical.test.security.service;


import co.soft.technical.test.security.model.Role;
import co.soft.technical.test.security.model.enums.TypeRole;

import java.util.Optional;

public interface IRoleService {
    public Optional<Role> getByTypeRole(TypeRole roleName);
}
