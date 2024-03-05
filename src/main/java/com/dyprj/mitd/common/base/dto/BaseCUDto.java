package com.dyprj.mitd.common.base.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@NoArgsConstructor
@Getter
public class BaseCUDto extends BaseCDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updateDateTime;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String updateUserId;

}
