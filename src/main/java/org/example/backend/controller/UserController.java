package org.example.backend.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
//import org.example.backend.exception.entity.User;
import org.example.backend.dto.reponse.ApiResponse;
import org.example.backend.service.UserService;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Slf4j
@RequestMapping
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Tag(name="User",description="APIs for user authentication")
public class UserController {
    UserService userService;
    @GetMapping("/dashboard")
    public String dashboard()
    {
        return "Dashboard";
    }




}
