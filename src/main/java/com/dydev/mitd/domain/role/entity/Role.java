package com.dydev.mitd.domain.role.entity;

import com.dydev.mitd.common.base.entity.BaseCUEntity;
import com.dydev.mitd.domain.role.enums.RoleTypes;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(max = 10)
    @Enumerated(EnumType.STRING)
    private RoleTypes roleId;

    @NotNull
    @Size(min = 1, max = 30)
    @Column
    private String roleName;

    public static Role getDefaultRole() {
        return Role.builder()
                .roleId(RoleTypes.ROLE_USER)
                .roleName(RoleTypes.ROLE_USER.getValue())
                .build();
    }

}
