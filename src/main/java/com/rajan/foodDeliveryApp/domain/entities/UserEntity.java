package com.rajan.foodDeliveryApp.domain.entities;

import com.rajan.foodDeliveryApp.domain.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class UserEntity implements UserDetails {
    // UserDetails interface provides default methods which is useful
    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String contact;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderEntity> orders;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name())); // kinda pointless I've used extractRole from token instead
    }

    @Override
    public String getUsername() {
        return getEmail();
    }



}

