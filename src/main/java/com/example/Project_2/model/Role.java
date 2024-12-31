package com.example.Project_2.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name; 
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_privillege", 
                joinColumns = @JoinColumn(name = "role_id") , 
                inverseJoinColumns = @JoinColumn(name = "privillege_id"))
    private List<Privillege> lt;
    
}
