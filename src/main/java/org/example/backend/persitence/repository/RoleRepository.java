package org.example.backend.persitence.repository;

import org.example.backend.persitence.entity.Role;
import org.example.backend.persitence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findRoleByRoleName(String roleName);
}
