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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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

    // -------------------------------------------------------------------------
    // POSITIVE TEST CASES
    // -------------------------------------------------------------------------

    /**
     * 1. Positive: Successfully verify Corporate ID and redirect to Admin Add page.
     */
    @Test
    void testVerifyCorp_Positive() throws Exception {
        mockMvc.perform(post("/users/verify-corp")
                .with(csrf())
                .param("corpId", "CORP123")) // Matches the @Value property
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/add"));
    }

    /**
     * 2. Positive: Successfully save a User with valid password, email, and role.
     */
    @Test
    void testSaveUser_Positive() throws Exception {
        mockMvc.perform(post("/users/add")
                .with(csrf())
                .param("userName", "John Doe")
                .param("email", "john@example.com")
                .param("role", "STAFF")
                .param("password", "Secure123")) // Matches length, upper, lower, and digit
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/list"));

        verify(userService, times(1)).saveUser(any());
    }

    /**
     * 3. Positive: Successfully delete a user by ID.
     */
    @Test
    void testDeleteUser_Positive() throws Exception {
        mockMvc.perform(get("/users/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/list"));

        verify(userService).deleteUser(1);
    }

    // -------------------------------------------------------------------------
    // NEGATIVE TEST CASES
    // -------------------------------------------------------------------------

    /**
     * 1. Negative: Fail Corporate ID verification (triggers Flash Attribute error).
     */
    @Test
    void testVerifyCorp_Negative_WrongId() throws Exception {
        mockMvc.perform(post("/users/verify-corp")
                .with(csrf())
                .param("corpId", "WRONG_ID"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("error"))
                .andExpect(redirectedUrl("/users/newAdmin"));
    }

    /**
     * 2. Negative: Fail to save user because password lacks uppercase/digit (triggers @Pattern).
     */
    @Test
    void testSaveUser_Negative_WeakPassword() throws Exception {
        mockMvc.perform(post("/users/add")
                .with(csrf())
                .param("userName", "John Doe")
                .param("email", "john@example.com")
                .param("role", "STAFF")
                .param("password", "weakpassword")) // Fails regex pattern
                .andExpect(status().isOk())
                .andExpect(view().name("user/user-add"))
                .andExpect(model().attributeHasFieldErrors("userDTO", "password"));
    }

    /**
     * 3. Negative: Fail to save user because role is invalid (triggers @Pattern).
     */
    @Test
    void testSaveUser_Negative_InvalidRole() throws Exception {
        mockMvc.perform(post("/users/add")
                .with(csrf())
                .param("userName", "John Doe")
                .param("email", "john@example.com")
                .param("role", "SUPERUSER") // Not in ADMIN|STAFF|VIEWER
                .param("password", "Secure123"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("userDTO", "role"));
    }
}
