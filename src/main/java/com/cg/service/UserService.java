package com.cg.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cg.dto.UserDTO;
import com.cg.entity.User;
import com.cg.repository.UserRepository;

@Service
public class UserService implements IUserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    // Entity -> DTO (inside service, NOT a mapper class)
    private UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                user.getRole()
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
    public List<UserDTO> getAllUsers() {
        return repo.findAll()
                   .stream()
                   .map(this::convertToDTO)
                   .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(int id) {
        User user = repo.findById(id).orElseThrow(() ->
            new RuntimeException("User not found with id: " + id)
        );
        return convertToDTO(user);
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        User saved = repo.save(convertToEntity(userDTO));
        return convertToDTO(saved);
    }

    @Override
    public UserDTO updateUser(int id, UserDTO userDTO) {
        User existing = repo.findById(id).orElseThrow(() ->
            new RuntimeException("User not found with id: " + id)
        );

        existing.setUserName(userDTO.getUserName());
        existing.setEmail(userDTO.getEmail());
        existing.setRole(userDTO.getRole());

        User updated = repo.save(existing);
        return convertToDTO(updated);
    }

    @Override
    public void deleteUser(int id) {
        repo.deleteById(id);
    }
}
