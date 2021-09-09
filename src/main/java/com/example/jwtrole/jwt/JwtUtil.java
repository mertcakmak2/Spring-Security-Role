package com.example.jwtrole.jwt;

import com.example.jwtrole.model.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
public class JwtUtil {

    private String secret = "secret_key";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        T claim = claimsResolver.apply(claims);
        return claim;
    }

    private Claims extractAllClaims(String token) {
        //token eksik karakterli gönderilirse burda patlar.
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims;
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username, List<UserRole> roles) {
        Map<String, Object> claims = new HashMap<>();

        for ( UserRole role : roles ) {
            claims.put("roles", roles);
        };
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                //.setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .setExpiration(getForOneDayTokenDate())
                .signWith(SignatureAlgorithm.HS256, secret).compact();
        return token;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Date getForOneDayTokenDate(){
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, 1440);
        return c.getTime();
    }
}
