package com.example.Project_2.service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.Project_2.dto.UserDto;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtIUtil {

    private String key="";
    

    public JwtIUtil(){
           try {
            SecretKey skey=Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
            key=Base64.getEncoder().encodeToString(skey.getEncoded());
            
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }

    private Key getKey(String key) {
        byte[] bk= Base64.getDecoder().decode(key);
        return Keys.hmacShaKeyFor(bk);
    }

    public String generateToken(UserDto dto) {
          
            Map<String, Object> claims=new HashMap<>();
            claims.put("username", dto.getUsername());

            String token=Jwts.builder()
                .setClaims(claims)
                .setSubject(dto.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1800*1000))
                .signWith(getKey(key))
                .compact();
          
            return token;
    }

    public Claims extractAllClaims(String token){
        Claims claims = Jwts.parser()
                            .setSigningKey(getKey(key))
                            .build()
                            .parseClaimsJws(token)
                            .getBody();
        
        return claims;
    }
      
    public <T> T extractClaim(String token, Function<Claims,T> claimResolver) {
          Claims claims=extractAllClaims(token);
          return claimResolver.apply(claims);
          
    }

    public String extractUsername(String token){
             String username= extractClaim(token, Claims::getSubject);
             return username;
    }

    public Date extractExpiration(String token){
            Date dt=extractClaim(token, Claims::getExpiration);
            return dt;
    }


    public Boolean isExpired(String token){
        Date dt=extractExpiration(token);
        return dt.before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        String username=extractUsername(token);
        if(username.equals(userDetails.getUsername()) && !isExpired(token)){
            return true;
        }
        return false;
    }

    

}
