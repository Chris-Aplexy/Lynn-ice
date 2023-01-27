package com.lynIceCreamApp.lynIceCreamApp.security.securityConfig;

import com.lynIceCreamApp.lynIceCreamApp.security.model.Role;
import com.lynIceCreamApp.lynIceCreamApp.security.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<com.lynIceCreamApp.lynIceCreamApp.security.model.User> authUser = Optional.ofNullable(userRepository.findByUsername(userName));
        authUser.orElseThrow(()-> new UsernameNotFoundException("user does not exist"));

        List<GrantedAuthority> authorities = authUser.get()
                .getRoles().stream()
                .map(Role::getRoleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new User(authUser.get().getUsername(),authUser.get().getPassword(),authorities);
    }
}