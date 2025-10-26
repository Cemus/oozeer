package com.cemus.oozeer.controller;


import com.cemus.oozeer.dto.AuthRequest;
import com.cemus.oozeer.model.User;
import com.cemus.oozeer.service.JwtService;
import com.cemus.oozeer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("unused")
@RequestMapping("/auth")
@RestController
public class AuthController {
    @Autowired
    private UserService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping("/user")
    @GetMapping("/profile")
    public String profile() {
        return "Nice";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the public authentication endpoint!";
    }

    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody User user) {
        return service.addUser(user);
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.username());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}

