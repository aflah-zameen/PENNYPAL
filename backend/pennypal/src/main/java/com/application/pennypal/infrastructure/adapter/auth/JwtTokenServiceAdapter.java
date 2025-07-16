package com.application.pennypal.infrastructure.adapter.auth;

import com.application.pennypal.application.port.TokenServicePort;
import com.application.pennypal.domain.entity.User;
import com.application.pennypal.infrastructure.security.jwt.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
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
