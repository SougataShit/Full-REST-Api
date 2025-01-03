package com.example.Project_2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Project_2.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByUsername(String name);

    void deleteByUsername(String name);
        
     
    
}
