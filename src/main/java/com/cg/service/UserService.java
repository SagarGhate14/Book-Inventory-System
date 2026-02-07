package com.cg.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cg.dto.UserDTO;
import com.cg.entity.User;
import com.cg.repository.UserRepository;

@Service
public class UserService implements IUserService {

      @Autowired
      private UserRepository userRepository;
      
      @Autowired
      private BCryptPasswordEncoder passwordEncoder;

    // Entity -> DTO (inside service, NOT a mapper class)
    private UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                user.getRole(),
                user.getPassword()
        );
    }

    // DTO -> Entity (inside service, NOT a mapper class)
    private User convertToEntity(UserDTO dto) {
        User user = new User();
        user.setUserId(dto.getUserId());
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User saveUser(User user) {
    	
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    
    
   

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
    
    public User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        User entity = new User();
        entity.setUserId(dto.getUserId());
        entity.setUserName(dto.getUserName());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());
        // Password handling is usually kept separate for security
        entity.setPassword(dto.getPassword()); 
        
        return entity;
    }
    
    public UserDTO toDTO(User entity) {
        if (entity == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setUserId(entity.getUserId());
        dto.setUserName(entity.getUserName());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        // For security, you might choose to omit the password in the DTO
        
        return dto;
    }
    
    
    public List<User> toEntityList(List<UserDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<User> entityList = new ArrayList<>();
        for (UserDTO dto : dtoList) {
            entityList.add(this.toEntity(dto));
        }
        return entityList;
    }
    
    public List<UserDTO> toDTOList(List<User> entityList) {
        if (entityList == null) {
            return null;
        }

        List<UserDTO> dtoList = new ArrayList<>();
        for (User entity : entityList) {
            dtoList.add(this.toDTO(entity));
        }
        return dtoList;
    }
}
