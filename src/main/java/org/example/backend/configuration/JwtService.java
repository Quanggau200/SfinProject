package org.example.backend.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.example.backend.dto.reponse.UserRegisterReponse;
import org.example.backend.persitence.entity.User;
import org.example.backend.persitence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

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
    // tạo ra refresh token
    public String generateRefreshToken(User user) {
        return Jwts.builder().setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .claim("role",user.getRoles())
                .setExpiration(new Date())
                .signWith(getSignerKey())
                .compact();
    }
    // hàm gen token
    public String generateToken(User user) {
        Date now=new Date();
        Date expiration=new Date(now.getTime() + 1000 * 60 * 60);
        return Jwts.builder().setSubject(user.getUsername()).
                setIssuedAt(new Date())
                .claim("role",user.getRoles())
                .signWith(getSignerKey())
                .expiration(expiration).
                compact();
    }
    // hàm giả mã lấy ra tên
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(getSignerKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignerKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public UserRegisterReponse extractUserFromToken(String token) {
        Claims claims = extractClaims(token);
        return UserRegisterReponse.builder()
                .username((String) claims.get("username"))
                .phone((String) claims.get("phone"))
                .email((String) claims.get("email"))
                .company((String) claims.get("company"))
                .build();
    }
    // hàm check token có tồn tại hay k
    public boolean isTokenValid(String token, UserDetails user ) {
        final String username = extractUsername(token);
        return (username.equals(user.getUsername())) && !isTokenExpired(token);
    }

    // hàm check token còn thời gian hay k
    private boolean isTokenExpired(String token) {
        Date exp = extractExpiration(token);
        if(exp==null)
        {
            return false;
        }
        return exp.before(new Date());
    }
    // hàm xử lý jwt
    public <T> T extractClaim(String token, Function<Claims,T> clazz) {
        final Claims claims=extractClaims(token);
        return clazz.apply(claims);

    }
    // hàm xử lý jwt
    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }
}
