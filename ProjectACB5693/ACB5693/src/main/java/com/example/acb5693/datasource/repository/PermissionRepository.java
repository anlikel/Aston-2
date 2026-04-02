package com.example.acb5693.datasource.repository;

import com.example.acb5693.datasource.model.entities.PermissionEntity;
import com.example.acb5693.datasource.model.entities.RoleEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<PermissionEntity, UUID> {

    Optional<PermissionEntity> findByCode(String roleName);

    boolean existsByCode(String roleName);

    void deleteByCode(String roleName);

    void deleteById(UUID id);

    List<PermissionEntity> findAll();
}
