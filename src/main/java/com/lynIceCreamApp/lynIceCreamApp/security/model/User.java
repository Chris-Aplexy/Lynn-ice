package com.lynIceCreamApp.lynIceCreamApp.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cascade;
import org.springframework.lang.Nullable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(updatable = false, unique = true)
    @NotEmpty
    private String username;
    private String password;
    @Email(message = "Enter valid email")
    private String email;
    @OneToMany(cascade = CascadeType.ALL, targetEntity = Address.class)
    private List<Address> addresses;
    @Nullable
    @OneToMany(cascade = CascadeType.ALL, targetEntity = Role.class)
    private List<Role> roles;

}
