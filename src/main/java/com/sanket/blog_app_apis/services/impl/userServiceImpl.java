package com.sanket.blog_app_apis.services.impl;

import com.sanket.blog_app_apis.entities.User;
import com.sanket.blog_app_apis.exceptions.ResourceNotFoundException;
import com.sanket.blog_app_apis.payloads.userDTO;
import com.sanket.blog_app_apis.repositories.UserRepo;
import com.sanket.blog_app_apis.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public userDTO createUser(userDTO userdto) {

        User user = this.dtoToUser(userdto);

        User savedUser = this.userRepo.save(user);

        return this.UserToDto(savedUser);
    }

    @Override
    public userDTO updateUser(userDTO userdto, Integer userId) {
        User user =  this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","User id",userId));
        user.setUsername(userdto.getUsername());
        user.setEmail(userdto.getEmail());
        user.setPassword(userdto.getPassword());
        user.setAbout(userdto.getAbout());

        User updatedUser = this.userRepo.save(user);

        return this.UserToDto((updatedUser));
    }

    @Override
    public userDTO getUserById(Integer userId) {
        User user =  this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","User id",userId));

        return this.UserToDto((user));
    }

    @Override
    public userDTO getUserByUsername(userDTO userdto) {
        String username = userdto.getUsername();
        User user =  this.userRepo.findByUsername(username);

        return this.UserToDto((user));
    }

    @Override
    public List<userDTO> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        return users.stream().map(this::UserToDto).toList();
    }

    @Override
    public void deleteUser(Integer userId) {
        User user =  this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","User id",userId));
        this.userRepo.delete(user);

    }
    private User dtoToUser(userDTO userdto){
        return this.modelMapper.map(userdto,User.class);

    }
    private userDTO UserToDto(User user){
        return this.modelMapper.map(user,userDTO.class);
    }
}
