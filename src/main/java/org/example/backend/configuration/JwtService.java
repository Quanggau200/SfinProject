package org.example.backend.configuration;

import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.example.backend.persitence.entity.User;
import org.example.backend.persitence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class JwtService
{
    @Value("${jwt.signerKey}")
    private String signerKey;

    private final UserRepository userRepository;
    private SecretKey getSignerKey()
    {
        byte[] keyBytes = signerKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String generateRefreshToken(User user) {
        return Jwts.builder().setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .claim("role",user.getRoles())
                .setExpiration(new Date())
                .signWith(getSignerKey())
                .compact();
    }
    public String generateToken(User user) {
        return Jwts.builder().setSubject(user.getUsername()).
                setIssuedAt(new Date())
                .signWith(getSignerKey()).
                compact();
    }
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(getSignerKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    public boolean isTokenValid(String token, User user ) {
        final String username = extractUsername(token);
        return (username.equals(user.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parser()
                .verifyWith(getSignerKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }
}
