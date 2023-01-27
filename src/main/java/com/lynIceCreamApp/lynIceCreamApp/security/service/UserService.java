package com.lynIceCreamApp.lynIceCreamApp.security.service;

import com.lynIceCreamApp.lynIceCreamApp.security.model.User;
import com.lynIceCreamApp.lynIceCreamApp.security.repository.UserRepository;
import com.lynIceCreamApp.lynIceCreamApp.security.securityConfig.AuthenticationRequest;
import com.lynIceCreamApp.lynIceCreamApp.security.securityConfig.AuthenticationResponse;
import com.lynIceCreamApp.lynIceCreamApp.security.securityConfig.MyUserDetailsService;
import com.lynIceCreamApp.lynIceCreamApp.security.securityConfig.jwt.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService myUserDetailsService;
    private final JWTUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, MyUserDetailsService myUserDetailsService, JWTUtil jwtTokenUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.myUserDetailsService = myUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public AuthenticationResponse/*ResponseEntity<?>*/ createAuthenticationToken(AuthenticationRequest authenticationRequest) throws Exception{
        log.info("entering auth");
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }catch (BadCredentialsException e){
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return new AuthenticationResponse(jwt);
    }

    @Secured({"ADMIN"})
    public List<User> userList(){
        return userRepository.findAll();
    }
}
