package com.example.acb5693.datasource.repository;

import com.example.acb5693.datasource.model.entities.RoleEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {

    Optional<RoleEntity> findByRoleName(String roleName);

    boolean existsByRoleName(String roleName);

    void deleteByRoleName(String roleName);

    void deleteById(UUID id);

    List<RoleEntity> findAll();
}
