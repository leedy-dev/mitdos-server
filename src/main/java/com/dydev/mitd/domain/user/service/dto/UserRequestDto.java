package com.dydev.mitd.domain.user.service.dto;

import com.dydev.mitd.common.validate.PatternDefine;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
public class UserRequestDto {

    @NotEmpty
    @Size(max = 30)
    private String userId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String dtype;

    @NotEmpty
    @Size(max = 200)
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty
    @Pattern(regexp = PatternDefine.PASSWORD_NUMBER_PATTERN)
    @Size(max = 20)
    private String password;

    @NotEmpty
    @Pattern(regexp = PatternDefine.EMAIL_PATTERN)
    @Size(max = 200)
    private String email;

    @Getter
    public static class Join extends UserRequestDto {

    }

    @Getter
    @Setter
    public static class Search {
        private String userId;
        private String userName;
    }

}
