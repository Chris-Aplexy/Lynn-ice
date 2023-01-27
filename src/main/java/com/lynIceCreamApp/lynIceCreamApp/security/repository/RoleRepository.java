package com.lynIceCreamApp.lynIceCreamApp.security.repository;

import com.lynIceCreamApp.lynIceCreamApp.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
