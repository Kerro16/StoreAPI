package com.kerro.Store.repository;

import com.kerro.Store.model.ERole;
import com.kerro.Store.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
