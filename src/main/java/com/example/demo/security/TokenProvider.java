package com.example.demo.security;

import com.example.demo.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
    private static final String SECRET_KEY = "FlRpX30pMQDbiAkm1FarbmVkD4RqISskGZmBFax5oGvxzXWWuzTR5JyskIHMIV9Ml0icgkpbi46AdvrcXfE6CmTUBc6IFbTPiD";

    public String create(UserEntity userEntity) {
        Date expiryDate = Date.from(
            Instant.now().plus(1, ChronoUnit.DAYS)
        );

        return Jwts.builder()
            .setSubject(userEntity.getId().toString())
            .setIssuer("demo app")
            .setIssuedAt(new Date())
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
            .compact();
    }

    public String validateAndGetUserId(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(token)
            .getBody();

        return claims.getSubject();
    }
}