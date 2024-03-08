package com.dydev.mitd.domain.user.entity;

import com.dydev.mitd.common.base.entity.BaseCUEntity;
import com.dydev.mitd.common.converter.BCryptoConverter;
import com.dydev.mitd.domain.role.entity.UserRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(
        name = "tb_user",
        uniqueConstraints = @UniqueConstraint(name = "uk_user", columnNames = "email")
)
public class User extends BaseCUEntity implements UserDetails {

    @Id
    private String userId;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, length = 10)
    private String nickname;

    @Convert(converter = BCryptoConverter.class)
    @Column(nullable = false, length = 200)
    private String password;

    @Column(nullable = false, length = 200, unique = true)
    private String email;

    @Column(columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime lastLoginDateTime;

    @Column(columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime passwordChangeDateTime;

    @Builder.Default
    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean accountExpired = false;

    @Builder.Default
    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean credentialExpired = false;

    @Builder.Default
    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean locked = false;

    @Builder.Default
    @Column(columnDefinition = "boolean default true", nullable = false)
    private boolean enabled = true;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRole> userRoles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !accountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    // roles
    public void addRole(UserRole userRole) {
        userRoles.add(userRole);
    }

    public void applyRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    // update
    public void updateLastLoginDateTime() {
        this.lastLoginDateTime = LocalDateTime.now();
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
        this.passwordChangeDateTime = LocalDateTime.now();
    }

}
