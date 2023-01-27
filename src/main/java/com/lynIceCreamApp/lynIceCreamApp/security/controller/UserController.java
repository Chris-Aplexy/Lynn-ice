package com.lynIceCreamApp.lynIceCreamApp.security.controller;

import com.lynIceCreamApp.lynIceCreamApp.security.model.User;
import com.lynIceCreamApp.lynIceCreamApp.security.securityConfig.AuthenticationRequest;
import com.lynIceCreamApp.lynIceCreamApp.security.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody User user){
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping("/auth")
    public ResponseEntity login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return ResponseEntity.ok(userService.createAuthenticationToken(authenticationRequest));
    }

    @GetMapping
    public ResponseEntity usersList(){
        return ResponseEntity.ok(userService.userList());
    }
}
