package com.example.usercontrollertests;

import com.example.controller.UserController;
import com.example.dto.UserDto;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
public class UserControllerDeleteUserTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Тест успешного удаления пользователя
     */
    @Test
    void deleteUser_WhenUserExists_ReturnOkWithUserDto() throws Exception {

        UserDto deletedUserDto = new UserDto("Aaaa", "aaa@mail.com", 25);
        deletedUserDto.setId(1L);
        deletedUserDto.setResult("OK");

        when(userService.deleteUserById(1L)).thenReturn(deletedUserDto);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Aaaa"))
                .andExpect(jsonPath("$.email").value("aaa@mail.com"))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.result").value("OK"));
    }

    /**
     * Тест удаления несуществующего пользователя
     */
    @Test
    void deleteUser_WhenUserNotFound_ReturnUserDtoWithError() throws Exception {

        UserDto errorUserDto = new UserDto(null, null, 0);
        errorUserDto.setResult("User not found with id: 999");

        when(userService.deleteUserById(999L)).thenReturn(errorUserDto);

        mockMvc.perform(delete("/api/users/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").isEmpty())
                .andExpect(jsonPath("$.email").isEmpty())
                .andExpect(jsonPath("$.age").value(0))
                .andExpect(jsonPath("$.result").value("User not found with id: 999"));
    }

    /**
     * Тест удаления с некорректным ID
     */
    @Test
    void deleteUser_WhenInvalidId_ReturnUserDtoWithError() throws Exception {

        UserDto errorUserDto = new UserDto(null, null, 0);
        errorUserDto.setResult("wrong id, should be in range from 1 to LongMax");

        when(userService.deleteUserById(-1L)).thenReturn(errorUserDto);

        mockMvc.perform(delete("/api/users/-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").isEmpty())
                .andExpect(jsonPath("$.email").isEmpty())
                .andExpect(jsonPath("$.age").value(0))
                .andExpect(jsonPath("$.result").value("wrong id, should be in range from 1 to LongMax"));
    }
}