package com.application.pennypal.infrastructure.adapter.out.auth;

import com.application.pennypal.application.port.out.service.TokenServicePort;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.infrastructure.config.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.cglib.core.ClassInfo;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Component
public class JwtTokenServiceAdapter implements TokenServicePort {
    private final JwtProperties props;
    private final SecretKey key;

    public JwtTokenServiceAdapter(SecretKey accessKey,
                                  JwtProperties jwtProperties){
        this.props = jwtProperties;
        this.key= accessKey;
    }

    @Override
    public String generateAccessToken(User user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("scope",user.getRoles())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+props.accessExpiration().toMillis()))
                .signWith(key)
                .compact();
    }

    @Override
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith( key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    @Override
    public Duration getExpireTime(String token){
        Date expire =  Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
        return Duration.between(Instant.now(),expire.toInstant());
    }

    @Override
    public boolean isValid(String token) {
        try{
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        }catch(JwtException | IllegalArgumentException ex){
            return false;
        }
    }


    @Override
    public Set<String> getRolesFromToken(String token) throws JwtException {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        Object scope = claims.get("scope");
        if (scope instanceof List) {

            return new HashSet<>((List<String>) scope);
        } else if (scope instanceof Set) {
            return (Set<String>) scope;
        }
        return Set.of();
    }

}
