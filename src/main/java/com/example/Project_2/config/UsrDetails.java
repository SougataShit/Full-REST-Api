package com.example.Project_2.config;


import java.util.Collection;
import java.util.List;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import com.example.Project_2.model.User;

public class UsrDetails implements UserDetails {

    private User user;

    private List<SimpleGrantedAuthority> authorities;

    
    
    public UsrDetails(User user) {
        this.user = user;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {   
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    public void setAuthorities(List<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }
    
}
