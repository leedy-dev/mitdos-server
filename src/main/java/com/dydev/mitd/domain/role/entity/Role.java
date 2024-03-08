package com.dydev.mitd.domain.role.entity;

import com.dydev.mitd.common.base.entity.BaseCUEntity;
import com.dydev.mitd.domain.role.enums.RoleTypes;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(
        name = "tb_role"
)
public class Role extends BaseCUEntity {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private RoleTypes roleId;

    @Column(nullable = false, length = 30)
    private String roleName;

    public static Role getDefaultRole() {
        return Role.builder()
                .roleId(RoleTypes.ROLE_USER)
                .roleName(RoleTypes.ROLE_USER.getValue())
                .build();
    }

}
