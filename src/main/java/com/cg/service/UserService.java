package com.cg.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cg.dto.UserDTO;
import com.cg.entity.User;
import com.cg.exception.GlobalException;
import com.cg.exception.UserNotFoundException;
import com.cg.repository.UserRepository;

@Service
public class UserService implements IUserService {

      @Autowired
      private UserRepository userRepository;
      
      @Autowired
      private BCryptPasswordEncoder passwordEncoder;


   //Get all the users from db
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //Find the user by Id
    @Override
    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    //Save the User in db
    @Override
    public User saveUser(User user) {
    	
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    
    //Delete User by Id
    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
    
    
    //Converting DTO to Entity
    public User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        User entity = new User();
        entity.setUserId(dto.getUserId());
        entity.setUserName(dto.getUserName());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());
        entity.setPassword(dto.getPassword()); 
        
        return entity;
    }
    
    //Converting entity to DTO
    public UserDTO toDTO(User entity) {
        if (entity == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setUserId(entity.getUserId());
        dto.setUserName(entity.getUserName());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        
        return dto;
    }
    
    //Converting DTOList to entityList
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
    
    //Converting entityList to DTOList
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
