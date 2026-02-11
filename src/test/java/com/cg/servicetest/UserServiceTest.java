package com.cg.servicetest;

import com.cg.dto.UserDTO;
import com.cg.entity.User;
import com.cg.exception.GlobalException;
import com.cg.exception.UserNotFoundException;
import com.cg.repository.UserRepository;
import com.cg.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	 @Mock
	    private UserRepository userRepository;

	    @Mock
	    private BCryptPasswordEncoder passwordEncoder;

	    @InjectMocks
	    private UserService userService;

	    private User sampleUser;
	    private UserDTO sampleDTO;

	    @BeforeEach
	    void setUp() {
	        // Prepare sample User entity
	        sampleUser = new User();
	        sampleUser.setUserId(101);
	        sampleUser.setUserName("JohnDoe");
	        sampleUser.setEmail("john@example.com");
	        sampleUser.setRole("ADMIN");
	        sampleUser.setPassword("RawPassword123");

	        // Prepare sample User DTO
	        sampleDTO = new UserDTO();
	        sampleDTO.setUserId(101);
	        sampleDTO.setUserName("JohnDoe");
	        sampleDTO.setEmail("john@example.com");
	        sampleDTO.setRole("ADMIN");
	        sampleDTO.setPassword("RawPassword123");
	    }

	    // POSITIVE TEST CASES

	    
	     // Positive: Successfully save a user.
	     // Verifies that the password is encrypted BEFORE saving to repository.
	     
	    @Test
	    void testSaveUser_Positive() {
	        // Arrange: Mock encoder and repository
	        when(passwordEncoder.encode("RawPassword123")).thenReturn("Encoded_Hash_XYZ");
	        when(userRepository.save(any(User.class))).thenReturn(sampleUser);

	        // Act
	        User result = userService.saveUser(sampleUser);

	        // Assert
	        assertNotNull(result);
	        assertEquals("Encoded_Hash_XYZ", result.getPassword());
	        verify(passwordEncoder, times(1)).encode("RawPassword123");
	        verify(userRepository, times(1)).save(sampleUser);
	    }

	    // Positive: Successfully find a user by a valid ID.
	     
	    @Test
	    void testGetUserById_Positive() {
	        // Arrange
	        when(userRepository.findById(101)).thenReturn(Optional.of(sampleUser));

	        // Act
	        User result = userService.getUserById(101);

	        // Assert
	        assertNotNull(result);
	        assertEquals("JohnDoe", result.getUserName());
	        verify(userRepository).findById(101);
	    }

	    // Positive: Verify the toDTO mapping logic.
	     
	    @Test
	    void testToDTO_Positive() {
	        // Act
	        UserDTO resultDTO = userService.toDTO(sampleUser);

	        // Assert
	        assertNotNull(resultDTO);
	        assertEquals(101, resultDTO.getUserId());
	        assertEquals("ADMIN", resultDTO.getRole());
	    }

	    // NEGATIVE TEST CASES

	    // Negative: Throw NoSuchElementException when ID is not found.
	    
	    @Test
	    void testGetUserById_Negative_NotFound() {
	        // Arrange
	        when(userRepository.findById(999)).thenReturn(Optional.empty());

	        // Act & Assert
	        // Expect your custom exception instead of NoSuchElementException
	        assertThrows(UserNotFoundException.class, () -> {
	            userService.getUserById(999);
	        });
	    }


	    //  Negative: Handle null input in toEntity conversion gracefully.
	     
	    @Test
	    void testToEntity_Negative_NullInput() {
	        // Act
	        User result = userService.toEntity(null);

	        // Assert
	        assertNull(result);
	    }

	    // Negative: Ensure toDTOList returns null if input entity list is null.
	     
	    @Test
	    void testToDTOList_Negative_NullInput() {
	        // Act
	        List<UserDTO> result = userService.toDTOList(null);

	        // Assert
	        assertNull(result);
	    }
}
