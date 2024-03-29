package com.dydev.mitd.domain.user.service.dto;

import com.dydev.mitd.common.base.dto.BaseCUDto;
import com.dydev.mitd.common.utils.CommonObjectUtils;
import com.dydev.mitd.common.validate.PatternDefine;
import com.dydev.mitd.domain.role.enums.RoleTypes;
import com.dydev.mitd.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class UserDto extends BaseCUDto {

    @NotEmpty
    @Size(max = 30)
    private String userId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String dtype;

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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime passwordChangeDateTime;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastLoginDateTime;

    private Set<RoleTypes> roles = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean accountExpired = false;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean credentialExpired = false;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean locked = false;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean enabled = false;

    public void applyRoles(User entity) {
        roles = entity.getUserRoles().stream()
                .filter(ur -> CommonObjectUtils.nonNull(ur.getId()))
                .map(ur -> ur.getId().getRoleId())
                .collect(Collectors.toSet());
    }

}
