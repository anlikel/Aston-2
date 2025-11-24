package com.example.usercontrollertests;

import com.example.controller.UserController;
import com.example.dto.UserDto;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerCreateUserTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Тест успешного создания пользователя
     */
    @Test
    void createUser_WhenUserNotExists_ReturnCreatedUser() throws Exception {

        UserDto createUserDto = new UserDto("Aaaa", "aaa@mail.com", 25);

        UserDto savedUserDto = new UserDto("Aaaa", "aaa@mail.com", 25);
        savedUserDto.setId(1L);
        savedUserDto.setResult("OK");

        when(userService.createUser(any(UserDto.class))).thenReturn(savedUserDto);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Aaaa"))
                .andExpect(jsonPath("$.email").value("aaa@mail.com"))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.result").value("OK"));
    }

    /**
     * Тест создания пользователя с существующим email
     */
    @Test
    void createUser_WhenUserExists_ReturnUserDtoWithError() throws Exception {

        UserDto createUserDto = new UserDto("Aaaa", "aaa@mail.com", 25);

        UserDto errorUserDto = new UserDto("Aaaa", "aaa@mail.com", 25);
        errorUserDto.setResult("User already exists with unique email: aaa@mail.com");

        when(userService.createUser(any(UserDto.class))).thenReturn(errorUserDto);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDto)))
                .andExpect(status().isOk()) // Теперь всегда 200 OK
                .andExpect(jsonPath("$.name").value("Aaaa"))
                .andExpect(jsonPath("$.email").value("aaa@mail.com"))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.result").value("User already exists with unique email: aaa@mail.com"));
    }

    /**
     * Тест создания пользователя с невалидным именем
     */
    @Test
    void createUser_WhenUserNameInvalid_ReturnUserDtoWithError() throws Exception {

        UserDto createUserDto = new UserDto("aaaa", "aaa@mail.com", 25);

        UserDto errorUserDto = new UserDto("aaaa", "aaa@mail.com", 25);
        errorUserDto.setResult("wrong name, should start from Uppercase letter");

        when(userService.createUser(any(UserDto.class))).thenReturn(errorUserDto);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("aaaa"))
                .andExpect(jsonPath("$.email").value("aaa@mail.com"))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.result").value("wrong name, should start from Uppercase letter"));
    }

    /**
     * Тест создания пользователя с невалидным email
     */
    @Test
    void createUser_WhenUserEmailInvalid_ReturnUserDtoWithError() throws Exception {

        UserDto createUserDto = new UserDto("Aaaa", "invalid-email", 25);

        UserDto errorUserDto = new UserDto("Aaaa", "invalid-email", 25);
        errorUserDto.setResult("wrong email");

        when(userService.createUser(any(UserDto.class))).thenReturn(errorUserDto);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Aaaa"))
                .andExpect(jsonPath("$.email").value("invalid-email"))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.result").value("wrong email"));
    }

    /**
     * Тест создания пользователя с невалидным возрастом
     */
    @Test
    void createUser_WhenUserAgeInvalid_ReturnUserDtoWithError() throws Exception {

        UserDto createUserDto = new UserDto("Aaaa", "aaa@mail.com", 150);

        UserDto errorUserDto = new UserDto("Aaaa", "aaa@mail.com", 150);
        errorUserDto.setResult("wrong age should be in range from 1 to 100");

        when(userService.createUser(any(UserDto.class))).thenReturn(errorUserDto);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Aaaa"))
                .andExpect(jsonPath("$.email").value("aaa@mail.com"))
                .andExpect(jsonPath("$.age").value(150))
                .andExpect(jsonPath("$.result").value("wrong age should be in range from 1 to 100"));
    }
}