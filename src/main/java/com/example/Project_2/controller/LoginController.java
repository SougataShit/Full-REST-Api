package com.example.Project_2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Project_2.dto.UserDto;
import com.example.Project_2.model.User;
import com.example.Project_2.service.UserService;

@RestController
public class LoginController {
    
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDto dto){
        return userService.login(dto);
    }

    @DeleteMapping("/userdelete")
    public String deleteUser(@RequestParam String name){
        return userService.deleteUser(name);
    }   
    
}
