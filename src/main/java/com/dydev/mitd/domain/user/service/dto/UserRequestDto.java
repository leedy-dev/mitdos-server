package com.dydev.mitd.domain.user.service.dto;

import com.dydev.mitd.common.validate.PatternDefine;
import com.dydev.mitd.domain.role.service.dto.UserRoleDto;
import com.dydev.mitd.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
public class UserRequestDto {

    @NotEmpty
    @Size(max = 30)
    private String userId;

    @NotEmpty
    @Size(max = 10)
    private String name;

    @NotEmpty
    @Size(max = 10)
    private String nickname;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty
    @Pattern(regexp = PatternDefine.PASSWORD_NUMBER_PATTERN)
    @Size(max = 20)
    private String password;

    @NotEmpty
    @Pattern(regexp = PatternDefine.EMAIL_PATTERN)
    @Size(max = 200)
    private String email;

    private Set<UserRoleDto> userRoles = new HashSet<>();

    public UserRequestDto(User entity) {
        this.userId = entity.getUserId();
        this.name = entity.getName();
        this.nickname = entity.getNickname();
        this.password = entity.getPassword();
        this.email = entity.getEmail();
    }

    @Getter
    public static class Join extends UserRequestDto {

    }

    @Getter
    public static class Update {
        @NotEmpty
        @Size(max = 10)
        private String name;

        @NotEmpty
        @Size(max = 10)
        private String nickname;

        @NotEmpty
        @Pattern(regexp = PatternDefine.EMAIL_PATTERN)
        @Size(max = 200)
        private String email;

        public Update(User entity) {
            this.name = entity.getName();
            this.nickname = entity.getNickname();
            this.email = entity.getEmail();
        }
    }

    @Getter
    public static class Password {
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotEmpty
        @Pattern(regexp = PatternDefine.PASSWORD_NUMBER_PATTERN)
        @Size(max = 20)
        private String password;
    }

    @Getter
    @Setter
    public static class Search {
        private String userId;
        private String userName;
    }

}
