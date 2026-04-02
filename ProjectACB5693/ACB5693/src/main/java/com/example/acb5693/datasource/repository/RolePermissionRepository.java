package com.example.acb5693.datasource.repository;

import com.example.acb5693.datasource.model.entities.RolePermissionEntity;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RolePermissionRepository extends JpaRepository<RolePermissionEntity, UUID> {

    // 1. Найти все связи для определенной роли
    List<RolePermissionEntity> findByRoleId(UUID roleId);

    // 2. Найти все связи для определенного разрешения
    List<RolePermissionEntity> findByPermissionId(UUID permissionId);

    // 3. Найти конкретную связь
    Optional<RolePermissionEntity> findByRoleIdAndPermissionId(UUID roleId, UUID permissionId);

    // 4. Проверить существует ли связь
    boolean existsByRoleIdAndPermissionId(UUID roleId, UUID permissionId);

    // 5. Удалить все связи для роли
    void deleteByRoleId(UUID roleId);

    // 6. Удалить все связи для разрешения
    void deleteByPermissionId(UUID permissionId);

    // 7. Удалить конкретную связь
    void deleteByRoleIdAndPermissionId(UUID roleId, UUID permissionId);

    // 8. Посчитать количество связей для роли
    long countByRoleId(UUID roleId);

    // 9. Посчитать количество связей для разрешения
    long countByPermissionId(UUID permissionId);

    // 10. Найти все связи с JOIN (для производительности)
    @Query("SELECT rp FROM RolePermissionEntity rp " +
        "JOIN FETCH rp.role " +
        "JOIN FETCH rp.permission " +
        "WHERE rp.role.id = :roleId")
    List<RolePermissionEntity> findWithDetailsByRoleId(@Param("roleId") UUID roleId);

    // 11. Найти все роли для разрешения
    @Query("SELECT rp.role FROM RolePermissionEntity rp WHERE rp.permission.id = :permissionId")
    Set<RolePermissionEntity> findRolesByPermissionId(@Param("permissionId") UUID permissionId);

    // 12. Найти все разрешения для роли
    @Query("SELECT rp.permission FROM RolePermissionEntity rp WHERE rp.role.id = :roleId")
    Set<RolePermissionEntity> findPermissionsByRoleId(@Param("roleId") UUID roleId);

    // 13. Проверить есть ли у роли хотя бы одно разрешение
    boolean existsByRoleId(UUID roleId);

    // 14. Проверить есть ли у разрешения хотя бы одна роль
    boolean existsByPermissionId(UUID permissionId);
}
