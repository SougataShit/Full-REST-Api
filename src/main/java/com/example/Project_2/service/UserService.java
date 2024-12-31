package com.example.Project_2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Project_2.dto.UserDto;
import com.example.Project_2.model.User;
import com.example.Project_2.repository.UserRepository;

@Service
public class UserService {
    
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtIUtil util;

    @Autowired
    private AuthenticationManager manager;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    public User saveUser(User user){
        User user1=new User();
        user1.setUsername(user.getUsername());
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user1);
    }

    @Transactional
    public String deleteUser(String name) {
        User user2=userRepository.findByUsername(name);
        if(user2!=null){
            userRepository.deleteByUsername(name);
            return "User Deleted Successfully...";
        }
        return "Unable to delete the user....";
    }

    public String login(UserDto dto) {
        Authentication authenticate= manager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));    
        if(authenticate.isAuthenticated()){
           return util.generateToken(dto);
        }
        return null;
    }
    




}
