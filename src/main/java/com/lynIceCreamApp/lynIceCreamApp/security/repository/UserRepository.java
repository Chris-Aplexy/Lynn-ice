package com.lynIceCreamApp.lynIceCreamApp.security.repository;

import com.lynIceCreamApp.lynIceCreamApp.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String userName);
}
