package com.example.usercontrollertests;

import com.example.controller.UserController;
import com.example.entities.UserEntity;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerGetAllUsersTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void getAllUsers_WhenUsersExist_ReturnListOfGetUserDto() throws Exception {

        UserEntity user1 = new UserEntity("Aaaa", "aaa@mail.com", 20);
        user1.setId(1L);

        UserEntity user2 = new UserEntity("Bbbb", "bbb@mail.com", 25);
        user2.setId(2L);

        List<UserEntity> users = Arrays.asList(user1, user2);

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Aaaa"))
                .andExpect(jsonPath("$[0].email").value("aaa@mail.com"))
                .andExpect(jsonPath("$[0].age").value(20))
                .andExpect(jsonPath("$[0].createdAt").exists())

                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Bbbb"))
                .andExpect(jsonPath("$[1].email").value("bbb@mail.com"))
                .andExpect(jsonPath("$[1].age").value(25))
                .andExpect(jsonPath("$[1].createdAt").exists());
    }

    @Test
    void getAllUsers_WhenNoUsers_ReturnEmptyList() throws Exception {

        when(userService.getAllUsers()).thenReturn(List.of());

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}
