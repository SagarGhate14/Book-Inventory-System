package com.cg.usertest;

import com.cg.controller.UserController;
import com.cg.dto.UserDTO;
import com.cg.entity.User;
import com.cg.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
// Inject the @Value field for the test environment
@TestPropertySource(properties = "app.security.corp-id=CORP123")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;



    @Test
    void testVerifyCorpId_Success_RedirectsToAdd() throws Exception {
        // Test valid Corporate ID matching the @TestPropertySource above
        mockMvc.perform(post("/users/verify-corp")
                        .param("corpId", "CORP123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/add"));
    }

    @Test
    void testSaveUser_MapsAndRedirects() throws Exception {
        when(userService.toEntity(any(UserDTO.class))).thenReturn(new User());

        mockMvc.perform(post("/users/add")
                        .flashAttr("userDTO", new UserDTO()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/list"));

        verify(userService, times(1)).saveUser(any(User.class));
    }
}
