package com.example.usercontrollertests;

import com.example.controller.UserController;
import com.example.dto.CreateUserDto;
import com.example.dto.GetUserDto;
import com.example.entities.UserEntity;
import com.example.exceptions.MyCustomException;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(UserController.class)
class UserControllerCreateUserTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createUser_WhenUserNotExists_ReturnCreatedUser() throws Exception {

        CreateUserDto createUserDto = new CreateUserDto("Aaaa", "aaa@mail.com", 25);
        UserEntity savedUser = new UserEntity();
        savedUser.setId(1L);
        savedUser.setName(createUserDto.getName());
        savedUser.setEmail(createUserDto.getEmail());
        savedUser.setAge(createUserDto.getAge());
        savedUser.setCreatedAt(LocalDateTime.now());

        GetUserDto expectedResponse = new GetUserDto(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getAge(),
                savedUser.getCreatedAt());

        when(userService.createUser(any(UserEntity.class))).thenReturn(savedUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(expectedResponse.getId()))
                .andExpect(jsonPath("$.name").value(expectedResponse.getName()))
                .andExpect(jsonPath("$.email").value(expectedResponse.getEmail()))
                .andExpect(jsonPath("$.age").value(expectedResponse.getAge()))
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    void createUser_WhenUserExists_ReturnConflict() throws Exception {

        CreateUserDto createUserDto = new CreateUserDto("Aaaa", "aaa@mail.com", 25);

        when(userService.createUser(any(UserEntity.class)))
                .thenThrow(new MyCustomException("User already exists with unique email: aaa@mail.com"));

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDto)))
                .andExpect(status().isConflict())
                .andExpect(content().string("User already exists with unique email: aaa@mail.com"));
    }
}
