package com.example.Project_2.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Project_2.model.Privillege;
import com.example.Project_2.model.User;
import com.example.Project_2.repository.UserRepository;

@Service
public class UsrDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=repository.findByUsername(username);
        if(user==null){
             throw new UsernameNotFoundException("Unable to find this user....");

        }
        
        UsrDetails usr=new UsrDetails(user);
        List<Privillege> authorities=user.getRole().getLt();
        List<SimpleGrantedAuthority> ls=authorities.stream().map(auth->new SimpleGrantedAuthority(auth.getName())).collect(Collectors.toList());
        ls.add(new SimpleGrantedAuthority("ROLE_"+user.getRole().getName()));
        usr.setAuthorities(ls);
        return usr;
    }
    
}
