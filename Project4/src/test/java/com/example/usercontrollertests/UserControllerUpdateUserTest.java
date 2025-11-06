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


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(UserController.class)
public class UserControllerUpdateUserTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void updateUser_WhenUserExistsAndValidData_ReturnUpdatedUser() throws Exception {

        Long userId = 1L;
        CreateUserDto updateUserDto = new CreateUserDto("Bbbbb", "bbb@mail.com", 30);

        UserEntity updatedUser = new UserEntity();
        updatedUser.setId(userId);
        updatedUser.setName(updateUserDto.getName());
        updatedUser.setEmail(updateUserDto.getEmail());
        updatedUser.setAge(updateUserDto.getAge());

        GetUserDto expectedResponse = new GetUserDto(
                updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getEmail(),
                updatedUser.getAge(),
                updatedUser.getCreatedAt());

        when(userService.updateUser(any(UserEntity.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedResponse.getId()))
                .andExpect(jsonPath("$.name").value(expectedResponse.getName()))
                .andExpect(jsonPath("$.email").value(expectedResponse.getEmail()))
                .andExpect(jsonPath("$.age").value(expectedResponse.getAge()))
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    void updateUser_WhenUserNotFound_ShouldReturnNotFound() throws Exception {

        Long userId = 999L;
        CreateUserDto updateUserDto = new CreateUserDto("Bbbbb", "bbb@mail.com", 30);

        when(userService.updateUser(any(UserEntity.class)))
                .thenThrow(new MyCustomException("User not found with id: " + userId));

        mockMvc.perform(put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserDto)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found with id: " + userId));
    }
}
