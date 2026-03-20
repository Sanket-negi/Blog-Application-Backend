package com.sanket.blog_app_apis.controllers;

import com.sanket.blog_app_apis.payloads.ApiResponses;
import com.sanket.blog_app_apis.payloads.userDTO;
import com.sanket.blog_app_apis.services.JwtService;
import com.sanket.blog_app_apis.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public userDTO register(@RequestBody userDTO userdto){
        userdto.setPassword(passwordEncoder.encode(userdto.getPassword()));
        return userService.createUser(userdto);
    }

    @PostMapping("/login")
    public String login(@RequestBody userDTO userDto){
        try{
            System.out.println("Login endpoint hit");
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(),userDto.getPassword()));
            return jwtService.generateToken(userDto.getUsername());
        }
        catch(AuthenticationException e){
            return "Login failed";
        }

    }

    @PostMapping("users/")
    public ResponseEntity<userDTO> createUser(@Valid @RequestBody userDTO userDto){
        userDTO createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    @PutMapping("users/{userId}")
    public ResponseEntity<userDTO> updateUser(@Valid @RequestBody userDTO userDto, @PathVariable Integer userId){
        userDTO updatedUserDto = this.userService.updateUser(userDto,userId);
        return ResponseEntity.ok(updatedUserDto);
    }

    @DeleteMapping("users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer uId){
        this.userService.deleteUser(uId);
        return new ResponseEntity<>(new ApiResponses("User deleted successfully",true), HttpStatus.OK);
    }

    @GetMapping("users/")
    public ResponseEntity<List<userDTO>> getAllUsers(){
        return ResponseEntity.ok(this.userService.getAllUsers());

    }

    @GetMapping("users/{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") Integer uid){
        userDTO user = this.userService.getUserById(uid);
        return ResponseEntity.ok(user);
    }
}
