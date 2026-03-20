package com.sanket.blog_app_apis.services;

import com.sanket.blog_app_apis.payloads.userDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    userDTO createUser(userDTO user);
    userDTO updateUser(userDTO user, Integer userId);
    userDTO getUserById(Integer userId);
    userDTO getUserByUsername(userDTO user);
    List<userDTO> getAllUsers();
    void deleteUser(Integer userId);
}
