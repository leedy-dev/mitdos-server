package com.dydev.mitd.domain.role.entity;

import com.dydev.mitd.common.base.entity.BaseCEntity;
import com.dydev.mitd.common.utils.CommonObjectUtils;
import com.dydev.mitd.domain.role.embedded.UserRoleId;
import com.dydev.mitd.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Persistable;

/**
 * - 복합 키 구성 (userRoleId)
 * 1. EmbeddedId
 * 복합 키를 하나의 객체로 취급하여 객체 지향적 설계 가능
 *
 * 2. IdClass
 * DB 지향적
 *
 * - Persistable 구현
 * JPA는 새로운 엔티티면 persist 아니면 merge 실행
 * merge 시 새로운 엔티티더라도 불필요한 select 쿼리 발생
 * 이를 방지하기 위해 isNew 메소드를 재정의하여 사용
 */
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "tb_user_role"
)
public class UserRole extends BaseCEntity implements Persistable<UserRoleId> {

    @EmbeddedId
    private UserRoleId userRoleId;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("roleId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @Override
    public UserRoleId getId() {
        return userRoleId;
    }

    @Override
    public boolean isNew() {
        return CommonObjectUtils.isNull(getCreateDateTime());
    }

}
