package messenger.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.*;
import messenger.dto.User;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.LinkedHashMap;

public class JWTService {

    private static JWTService instance = null;

    private JWTService() {
    }

    public static synchronized JWTService getInstance() {
        if (instance == null) {
            instance = new JWTService();
        }
        return instance;
    }

    private long ttlMillis = 1000 * 60 * 60;
    private final byte[] apiKeySecretBytes = ("123451234512345123451234512345123451" +
            "234512345123451234512345123451234512345123451234512345123" +
            "45123451234512345123451234512345123451234512345123451234512" +
            "3451234512345123451234" +
            "51234512345123451234512345123451234512345123451234512345123451234512345").getBytes();

    public void setTtlMillis(long ttlMillis) {
        this.ttlMillis = ttlMillis;
    }

    public String generateJWToken(String name, String email) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

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
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(apiKeySecretBytes).build().parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            userFromToken = body.get("user", LinkedHashMap.class);
        } catch (JwtException e) {
            System.out.println("Token is not validity");
            e.printStackTrace();
        }
        return new User(null, userFromToken.get("name"), userFromToken.get("email"), null);
    }
}
