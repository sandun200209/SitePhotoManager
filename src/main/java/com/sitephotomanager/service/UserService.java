package com.sitephotomanager.service;

import com.sitephotomanager.entity.Role;
import com.sitephotomanager.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByEmail(String email);

    User registerUser(String fullName, String email, String password, Role role);

    User updateUser(Long id, String fullName, String email, Role role, String newPassword);

    void deleteUser(Long id);

    boolean existsByEmail(String email);
}
