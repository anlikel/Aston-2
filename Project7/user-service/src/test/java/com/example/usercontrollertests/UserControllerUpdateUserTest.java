package com.example.usercontrollertests;

import com.example.controller.UserController;
import com.example.dto.UserDto;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@TestPropertySource(properties = {
        "spring.cloud.config.enabled=false",
        "spring.cloud.bootstrap.enabled=false"
})
public class UserControllerUpdateUserTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Тест успешного обновления пользователя
     */
    @Test
    void updateUser_WhenUserExistsAndValidData_ReturnUpdatedUser() throws Exception {

        Long userId = 1L;
        UserDto updateUserDto = new UserDto("Bbbbb", "bbb@mail.com", 30);

        UserDto updatedUserDto = new UserDto("Bbbbb", "bbb@mail.com", 30);
        updatedUserDto.setId(userId);
        updatedUserDto.setResult("OK");

        when(userService.updateUser(any(UserDto.class), eq(userId))).thenReturn(updatedUserDto);

        mockMvc.perform(put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value("Bbbbb"))
                .andExpect(jsonPath("$.email").value("bbb@mail.com"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.result").value("OK"));
    }

    /**
     * Тест обновления несуществующего пользователя
     */
    @Test
    void updateUser_WhenUserNotFound_ReturnUserDtoWithError() throws Exception {

        Long userId = 999L;
        UserDto updateUserDto = new UserDto("Bbbbb", "bbb@mail.com", 30);

        UserDto errorUserDto = new UserDto("Bbbbb", "bbb@mail.com", 30);
        errorUserDto.setResult("User not found with id: 999");

        when(userService.updateUser(any(UserDto.class), eq(userId))).thenReturn(errorUserDto);

        mockMvc.perform(put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bbbbb"))
                .andExpect(jsonPath("$.email").value("bbb@mail.com"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.result").value("User not found with id: 999"));
    }

    /**
     * Тест обновления пользователя с невалидными данными
     */
    @Test
    void updateUser_WhenInvalidData_ReturnUserDtoWithError() throws Exception {

        Long userId = 1L;
        UserDto updateUserDto = new UserDto("bbbbb", "invalid-email", 150);

        UserDto errorUserDto = new UserDto("bbbbb", "invalid-email", 150);
        errorUserDto.setResult("wrong name, should start from Uppercase letter");

        when(userService.updateUser(any(UserDto.class), eq(userId))).thenReturn(errorUserDto);

        mockMvc.perform(put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("bbbbb"))
                .andExpect(jsonPath("$.email").value("invalid-email"))
                .andExpect(jsonPath("$.age").value(150))
                .andExpect(jsonPath("$.result").value("wrong name, should start from Uppercase letter"));
    }

    /**
     * Тест обновления пользователя с невалидным ID
     */
    @Test
    void updateUser_WhenInvalidId_ReturnUserDtoWithError() throws Exception {

        Long invalidUserId = -1L;
        UserDto updateUserDto = new UserDto("Bbbbb", "bbb@mail.com", 30);

        UserDto errorUserDto = new UserDto("Bbbbb", "bbb@mail.com", 30);
        errorUserDto.setResult("wrong id, should be in range from 1 to LongMax");

        when(userService.updateUser(any(UserDto.class), eq(invalidUserId))).thenReturn(errorUserDto);

        mockMvc.perform(put("/api/users/{id}", invalidUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bbbbb"))
                .andExpect(jsonPath("$.email").value("bbb@mail.com"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.result").value("wrong id, should be in range from 1 to LongMax"));
    }
}