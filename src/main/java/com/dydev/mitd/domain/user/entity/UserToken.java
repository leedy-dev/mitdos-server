package com.dydev.mitd.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(
        name = "tb_user_token"
)
public class UserToken {

    @Id
    private String userId;

    @NotNull
    @Column
    private String refreshToken;

    // Update
    @Builder.Default
    @LastModifiedDate
    @Column(columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime updateDateTime = LocalDateTime.now();

    // update
    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        this.updateDateTime = LocalDateTime.now();
    }

    // validate
    public boolean validateRefreshToken(String refreshToken) {
        return this.refreshToken.equals(refreshToken);
    }

}
