package com.dydev.mitd.domain.user.entity;

import com.dydev.mitd.common.base.entity.BaseCUEntity;
import com.dydev.mitd.common.converter.BCryptoConverter;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(
        name = "tb_user"
)
@DiscriminatorColumn
public class User extends BaseCUEntity implements UserDetails {

    @Id
    private String userId;

    @Column(insertable = false, updatable = false)
    private String dtype;

    @Column(nullable = false, length = 200)
    private String name;

    @Convert(converter = BCryptoConverter.class)
    @Column(nullable = false, length = 200)
    private String password;

    @Column(nullable = false, length = 200)
    private String email;

    @Column(columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime lastLoginDateTime;

    @Builder.Default
    @Column(columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime passwordChangeDateTime = LocalDateTime.now();

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

//    @Builder.Default
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<UserRole> roles = new ArrayList<>();

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

    public void updateLastLoginDateTime() {
        this.lastLoginDateTime = LocalDateTime.now();
    }

}
