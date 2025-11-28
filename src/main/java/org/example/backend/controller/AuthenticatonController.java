package org.example.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.example.backend.configuration.JwtService;
import org.example.backend.dto.reponse.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.dto.reponse.AuthenticationReponse;
import org.example.backend.dto.reponse.UserRegisterReponse;
import org.example.backend.dto.request.AuthenticationRequest;
import org.example.backend.dto.request.UserRegisterRequest;
import org.example.backend.persitence.entity.User;
import org.example.backend.persitence.repository.UserRepository;
import org.example.backend.service.AuthenticationService;
import org.example.backend.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Locale;

@Slf4j
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/sfinvietnam/auth")
@ResponseStatus(HttpStatus.CREATED)
public class AuthenticatonController {
    private AuthenticationService authenticationService;
    private UserRepository userRepository;
    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/register-new-user")
    public ResponseEntity<ApiResponse<AuthenticationReponse>>register
            (@Valid @RequestBody UserRegisterRequest request)
    {
        AuthenticationReponse reponseRegister =userService.register(request);
        ResponseCookie cookie= ResponseCookie.from("access_token",reponseRegister.getToken())
                .httpOnly(true)
                .sameSite("none")
                .maxAge(1000 * 60 * 60 * 24 * 7)
                .secure(true)
                .path("/")
                .build();
        return ResponseEntity.ok().
                header(HttpHeaders.SET_COOKIE,cookie.toString())
                .body(
                        new ApiResponse<>(
                        200,
                        "success",
                        "Register Successfully",
                        reponseRegister));
    }
    // get user
    @GetMapping("/get-user")
    public ApiResponse<UserRegisterReponse> getProfileUser(
            @CookieValue(name="access_token",required = false) String token )
    {
        if (token == null || token.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Token is empty");
        }
        String username= jwtService.extractUsername(token);
        User userFind=userRepository.findByUsername(username)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        UserRegisterReponse userRegisterReponse=new UserRegisterReponse();
        userRegisterReponse.setId(userFind.getId());
        userRegisterReponse.setUsername(userFind.getUsername());
        userRegisterReponse.setEmail(userFind.getEmail());
        userRegisterReponse.setCompany(userFind.getCompany());
        userRegisterReponse.setActive(userFind.isActive());
        userRegisterReponse.setPhone(userFind.getPhone());
        userRegisterReponse.setCreatedAt(userFind.getCreatedAt());
        userRegisterReponse.setUpdateAt(userFind.getUpdatedAt());
        return new ApiResponse<>(
                200,
                "success",
                "Get user Successfully",
                userRegisterReponse
        );
    }

    @PostMapping("/login")
    @Operation(summary = "Authenicaton admin",description = "Authentication user with username and password")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "Login successful")

    public ResponseEntity<ApiResponse<AuthenticationReponse>> authenticate(@Valid @RequestBody AuthenticationRequest request)
    {
    AuthenticationReponse response=authenticationService.authenticated(request);
    ResponseCookie accesstooken=ResponseCookie.from("access_token",response.getToken())
            .httpOnly(true)
            .sameSite("none")
            .secure(true)
            .path("/")
            .maxAge(1000 * 60 * 60 * 24 * 7)
            .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accesstooken.toString())
                .body(new ApiResponse<>(
                        200,
                        "Success",
                        "Login successful",
                        response
                ));
    }

}
