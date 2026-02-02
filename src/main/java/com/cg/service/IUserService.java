package com.cg.service;

import java.util.List;

import com.cg.dto.UserDTO;

public interface IUserService {

    List<UserDTO> getAllUsers();
    UserDTO getUserById(int id);

    UserDTO saveUser(UserDTO userDTO);
    UserDTO updateUser(int id, UserDTO userDTO);

    void deleteUser(int id);
}