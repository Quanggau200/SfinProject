package org.example.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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
import org.example.backend.service.AuthenticationService;
import org.example.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/sfinvietnam/auth")
@ResponseStatus(HttpStatus.CREATED)
public class AuthenticatonController {
    private UserService userService;
    private AuthenticationService authenticationService;
    @PostMapping("/register-new-user")
    public ApiResponse<UserRegisterReponse>registerAcc(@Valid @RequestBody UserRegisterRequest request)
    {
        UserRegisterReponse reponse=userService.register(request);
        return new ApiResponse<>(
                200,
                "success",
                "Register Successfully",
                reponse
        );
    }
    @PostMapping("/login")
    @Operation(summary = "Authenicaton admin",description = "Authentication user with username and password")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "Login successful")
    public ApiResponse<AuthenticationReponse>authenticate(
            @Valid @RequestBody AuthenticationRequest request)
    {
        return new ApiResponse<>(
                200,
            "Success",
        "Login successful",
        authenticationService.authenticated(request)
        );
    }

}
