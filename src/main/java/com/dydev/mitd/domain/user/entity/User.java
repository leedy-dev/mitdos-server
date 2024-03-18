package com.dydev.mitd.domain.user.entity;

import com.dydev.mitd.common.base.entity.BaseCUEntity;
import com.dydev.mitd.common.converter.BCryptoConverter;
import com.dydev.mitd.domain.role.entity.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
@DynamicUpdate
public class User extends BaseCUEntity implements UserDetails {

    @Id
    private String userId;

    @NotNull
    @Size(min = 1, max = 200)
    @Column
    private String name;

    @NotNull
    @Size(min = 1, max = 10)
    @Column
    private String nickname;

    @NotNull
    @Size(max = 200)
    @Convert(converter = BCryptoConverter.class)
    @Column
    private String password;

    @NotNull
    @Size(min = 1, max = 200)
    @Column(unique = true)
    private String email;

    @Column(columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime lastLoginDateTime;

    @Column(columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime passwordChangeDateTime;

    @Builder.Default
    @NotNull
    @Column(columnDefinition = "boolean default false")
    private boolean accountExpired = false;

    @Builder.Default
    @NotNull
    @Column(columnDefinition = "boolean default false")
    private boolean credentialExpired = false;

    @Builder.Default
    @NotNull
    @Column(columnDefinition = "boolean default false")
    private boolean locked = false;

    @Builder.Default
    @NotNull
    @Column(columnDefinition = "boolean default true")
    private boolean enabled = true;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRole> userRoles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles.stream()
                .map(ur -> (GrantedAuthority) () -> ur.getUserRoleId().getRoleId().name())
                .collect(Collectors.toSet());
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
    public void clearRoles() {
        this.userRoles.clear();
    }

    public void addRole(UserRole userRole) {
        this.userRoles.add(userRole);
    }

    public void addRoles(Set<UserRole> userRoles) {
        this.userRoles.addAll(userRoles);
    }

    public void removeRole(UserRole userRole) {
        this.userRoles.remove(userRole);
    }

    public void applyRoles(Set<UserRole> userRoles) {
        this.userRoles.stream()
                .filter(ur -> !userRoles.contains(ur))
                .forEach(ur -> this.userRoles.remove(ur));

        userRoles.stream()
                .filter(ur -> !this.userRoles.contains(ur))
                .forEach(ur -> this.userRoles.add(ur));
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
