package com.example.Project_2.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.Project_2.service.JwtIUtil;

import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtIUtil util;

    @Autowired
    private UsrDetailsService usrDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException{
        String authToken= request.getHeader("Authorization");
        String token=null;
        String username=null;

        if(authToken!=null && authToken.startsWith("Bearer ")){
           token=authToken.substring(7);
           username=util.extractUsername(token);
           
        }

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
               UserDetails usrdtls= usrDetailsService.loadUserByUsername(username);
               if(util.validateToken(token,usrdtls)){
                   UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(usrdtls, null,usrdtls.getAuthorities());
                   usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                   SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                   
               }
        }
        filterChain.doFilter(request, response);
    }
     
    
}





