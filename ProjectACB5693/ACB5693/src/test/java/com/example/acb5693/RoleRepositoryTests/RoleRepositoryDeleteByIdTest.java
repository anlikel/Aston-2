package com.example.acb5693.RoleRepositoryTests;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;

import com.example.acb5693.RepositoryTestAbstract;
import com.example.acb5693.datasource.model.entities.RoleEntity;
import com.example.acb5693.datasource.repository.RoleRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@ActiveProfiles("test")
@Sql(scripts = "/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class RoleRepositoryDeleteByIdTest extends RepositoryTestAbstract {
    @Autowired
    private RoleRepository roleRepository;



    @Test
    void deleteById_shouldDeleteRoleWhenIdExists() {
        UUID roleId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        List<RoleEntity> allRoles = roleRepository.findAll();
        assertEquals(2, allRoles.size());
        assertThat(roleRepository.findById(roleId)).isPresent();

        roleRepository.deleteById(roleId);
        assertThat(roleRepository.findById(roleId)).isNotPresent();
        allRoles = roleRepository.findAll();
        assertEquals(1, allRoles.size());
    }
//
//    @Test
//    @Transactional
//    void deleteById_shouldDoNothingWhenIdDoesNotExist() {
//        // Получаем текущее количество ролей
//        List<RoleEntity> rolesBefore = roleRepository.findAll();
//        int countBefore = rolesBefore.size();
//
//        // Пытаемся удалить несуществующую роль
//        UUID nonExistentId = UUID.randomUUID();
//        roleRepository.deleteById(nonExistentId);
//
//        // Проверяем, что количество ролей не изменилось
//        List<RoleEntity> rolesAfter = roleRepository.findAll();
//        assertThat(rolesAfter).hasSize(countBefore);
//    }
//
//    @Test
//    @Transactional
//    void deleteById_shouldWorkWithValidUUIDFormat() {
//        // Удаляем роль
//        roleRepository.deleteById(roleId1);
//
//        // Проверяем, что роль удалена
//        assertThat(roleRepository.findById(roleId1)).isEmpty();
//
//        // Проверяем, что другая роль не затронута
//        assertThat(roleRepository.findById(roleId2)).isPresent();
//    }
}
