package com.example.Project_2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private UsrDetailsService service;
    
    @Autowired
    private JwtFilter jwtFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable());
            http
            .authorizeHttpRequests((request)->request.
            requestMatchers("/login").permitAll()
            .requestMatchers("/register").permitAll()
            .requestMatchers("/userdelete").permitAll()
            .anyRequest().authenticated());
            http.httpBasic(Customizer.withDefaults());
            http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
            return http.build();
            
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean 
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setUserDetailsService(service);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean 
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
           return configuration.getAuthenticationManager();
    }
}
