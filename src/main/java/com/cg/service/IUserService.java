package com.cg.service;

import java.util.List;

import com.cg.dto.UserDTO;
import com.cg.entity.User;

public interface IUserService {

    List<User> getAllUsers();
    User getUserById(int id);

    User saveUser(User user);
    User updateUser(int id, UserDTO userDTO);

    void deleteUser(int id);
}