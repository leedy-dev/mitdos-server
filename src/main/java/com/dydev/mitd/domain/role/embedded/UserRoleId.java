package com.dydev.mitd.domain.role.embedded;

import com.dydev.mitd.domain.role.enums.RoleTypes;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Builder
@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
public class UserRoleId implements Serializable {

    @NotNull
    @Column(name = "user_id")
    private String userId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role_id")
    private RoleTypes roleId;

}
