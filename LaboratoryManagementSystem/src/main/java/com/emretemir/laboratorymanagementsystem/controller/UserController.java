package com.emretemir.laboratorymanagementsystem.controller;

import com.emretemir.laboratorymanagementsystem.dto.AuthRequest;
import com.emretemir.laboratorymanagementsystem.dto.CreateUserRequest;
import com.emretemir.laboratorymanagementsystem.model.User;
import com.emretemir.laboratorymanagementsystem.service.JWTService;
import com.emretemir.laboratorymanagementsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
public class UserController {

    private final UserService userService;

    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, JWTService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the laboratory management system";
    }

    @PostMapping("/addNewUser")
    public User addUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @GetMapping("/user")
    public String getUserString() {
        return "This is USER!";
    }

    @GetMapping("/testUser")
    public String testUserAccess() {
        return "User access confirmed!";
    }

    @PostMapping("/generateToken")
    public String generateToken(@RequestBody AuthRequest request) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        if (authenticate.isAuthenticated()) {
            return jwtService.generateToken(request.username());
        }
        throw new UsernameNotFoundException("invalid username {} " + request.username());
    }

    @GetMapping("/admin")
    public String getAdminString() {
        return "This is ADMIN!";
    }
}
