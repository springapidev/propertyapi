package com.coderbd.repository;

import com.coderbd.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * @author Md. Rajaul Islam
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByRoleName(String rolename);
    Optional<Role> findByRoleName(String rolename);
}
