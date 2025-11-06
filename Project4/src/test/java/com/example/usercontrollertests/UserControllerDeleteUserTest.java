package com.example.usercontrollertests;

import com.example.controller.UserController;
import com.example.exceptions.MyCustomException;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerDeleteUserTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void deleteUser_WhenUserExists_ReturnNoContent() throws Exception {

        when(userService.deleteUserById(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("deleted"));
    }

    @Test
    void deleteUser_WhenUserNotFound_ReturnNotFound() throws Exception {

        when(userService.deleteUserById(999L))
                .thenThrow(new MyCustomException("User not found with id: 999"));

        mockMvc.perform(delete("/api/users/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found with id: 999"));
    }

    @Test
    void deleteUser_WhenInvalidId_ReturnBadRequest() throws Exception {

        when(userService.deleteUserById(-1L))
                .thenThrow(new MyCustomException("wrong id, should be in range from 1 to LongMax"));

        mockMvc.perform(delete("/api/users/-1L"))
                .andExpect(status().isBadRequest());
    }
}
