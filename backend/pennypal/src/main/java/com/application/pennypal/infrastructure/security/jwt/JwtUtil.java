package com.application.pennypal.infrastructure.security.jwt;

import com.application.pennypal.application.port.TokenServicePort;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.domain.user.valueObject.Roles;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class JwtUtil  {
    private final SecretKey key=null;
    private final Long accessTokenExpiration=0L;


    public String generateAccessToken(User user){
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("scope",user.getRoles())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+accessTokenExpiration))
                .signWith(key)
                .compact();
    }

    public String getUsernameFromToken(String token) throws JwtException{
    return Jwts.parser()
            .verifyWith( key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }

    @SuppressWarnings("unchecked")

    public Set<Roles> getRolesFromToken(String token) throws JwtException {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            Object scope = claims.get("scope");
            if (scope instanceof List) {
                return new HashSet<>((List<Roles>) scope);
            } else if (scope instanceof Set) {
                return (Set<Roles>) scope;
            }
            return Set.of();
    }

    public String refreshAccessToken(String refreshToken, String ipAddress) {
        return "";
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }



}