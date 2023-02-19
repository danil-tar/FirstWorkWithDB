package org.example.learn.spring.messenger.service;

import io.jsonwebtoken.*;
import org.example.learn.spring.messenger.dto.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.LinkedHashMap;

@Service
public class JWTService {

    private JWTService() {
    }

    private long ttlMillis = 1000 * 60 * 60;
    @Value("${secretKey}")
    private String secretKey;

    public void setTtlMillis(long ttlMillis) {
        this.ttlMillis = ttlMillis;
    }

    public String generateJWToken(String name, String email) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        Key signingKey = new SecretKeySpec(secretKey.getBytes(), signatureAlgorithm.getJcaName());

//        System.out.println(signingKey);

        User user = new User();
        user.setEmail(email);
        user.setName(name);

        JwtBuilder builder = Jwts.builder()
                .setId(email)
                .setIssuedAt(now)
                .claim("user", user)
                .signWith(signingKey, signatureAlgorithm);

        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    public User testValidity(String token) {

        LinkedHashMap<String, String> userFromToken = new LinkedHashMap<>();

        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            userFromToken = body.get("user", LinkedHashMap.class);
        } catch (JwtException e) {
            System.out.println("Token is not validity");
            e.printStackTrace();
        }
        return new User(null, userFromToken.get("name"), userFromToken.get("email"), null);
    }
}
