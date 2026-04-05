package com.example.usercontrollertests;

import com.example.controller.UserController;
import com.example.dto.UserDto;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@TestPropertySource(properties = {
        "spring.cloud.config.enabled=false",
        "spring.cloud.bootstrap.enabled=false"
})
public class UserControllerGetUserTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    /**
     * Тест успешного получения пользователя по ID
     */
    @Test
    void getUserById_WhenFoundById_ReturnUserDto() throws Exception {

        UserDto userDto = new UserDto("Aaaa", "aaa@mail.com", 20);
        userDto.setId(1L);
        userDto.setResult("OK");

        when(userService.getUserById(1L)).thenReturn(userDto);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Aaaa"))
                .andExpect(jsonPath("$.email").value("aaa@mail.com"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.result").value("OK"));
    }

    /**
     * Тест получения несуществующего пользователя
     */
    @Test
    void getUserById_WhenNotFoundById_ReturnUserDtoWithError() throws Exception {

        UserDto errorUserDto = new UserDto(null, null, 0);
        errorUserDto.setResult("User not found with id: 999");

        when(userService.getUserById(999L)).thenReturn(errorUserDto);


        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").isEmpty())
                .andExpect(jsonPath("$.email").isEmpty())
                .andExpect(jsonPath("$.age").value(0))
                .andExpect(jsonPath("$.result").value("User not found with id: 999"));
    }
}