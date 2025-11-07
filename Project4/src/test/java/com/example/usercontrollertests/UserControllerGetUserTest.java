package com.example.usercontrollertests;

import com.example.controller.UserController;
import com.example.dto.GetUserDto;
import com.example.entities.UserEntity;
import com.example.exceptions.MyCustomException;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(UserController.class)
public class UserControllerGetUserTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    /**
     * Тест успешного получения пользователя по ID
     */
    @Test
    void getUserById_WhenFoundById_ReturnGetUserDto() throws Exception {

        UserEntity user = new UserEntity("Aaaa", "aaa@mail.com", 20);
        user.setId(1L);

        GetUserDto getUserDto = new GetUserDto();
        getUserDto.setId(user.getId());
        getUserDto.setName(user.getName());
        getUserDto.setEmail(user.getEmail());
        getUserDto.setAge(user.getAge());
        getUserDto.setCreatedAt(user.getCreatedAt());

        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(getUserDto.getId()))
                .andExpect(jsonPath("$.name").value(getUserDto.getName()))
                .andExpect(jsonPath("$.email").value(getUserDto.getEmail()))
                .andExpect(jsonPath("$.age").value(getUserDto.getAge()))
                .andExpect(jsonPath("$.createdAt").exists());
    }

    /**
     * Тест получения несуществующего пользователя
     */
    @Test
    void getUserById_WhenNotFoundById_ReturnNotFound() throws Exception {

        when(userService.getUserById(999L))
                .thenThrow(new MyCustomException("User not found with id: 999"));

        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found with id: 999"));
    }
}