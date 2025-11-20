package org.example.backend.service;

import com.nimbusds.jose.JWSAlgorithm;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.configuration.JwtService;
import org.example.backend.dto.reponse.AuthenticationReponse;
import org.example.backend.dto.request.AuthenticationRequest;
import org.example.backend.persitence.entity.User;
import org.example.backend.persitence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    UserRepository userRepository;
    JwtService jwtService;
    PasswordEncoder passwordEncoder=new BCryptPasswordEncoder(15);


    public AuthenticationReponse authenticated(AuthenticationRequest request)
    {
        var userLogin = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found!"));
        boolean authenticated=passwordEncoder.matches(request.getPassword(),userLogin.getPasswordHash());
        var token= jwtService.generateToken(userLogin);
        var refreshToken=jwtService.generateRefreshToken(userLogin);
        return  AuthenticationReponse.builder().token(token).authenticated(true).build();
    }



}
