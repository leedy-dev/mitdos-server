package com.dydev.mitd.domain.menu.service.dto;

import com.dydev.mitd.common.base.dto.BaseCUDto;
import com.dydev.mitd.domain.role.enums.RoleTypes;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class MenuDto extends BaseCUDto {

    private Long upperMenuId;

    @NotEmpty
    @Size(max = 30)
    private String menuName;

    @NotNull
    private RoleTypes auth;

    private String url;

    @NotNull
    private Integer level;

    @NotNull
    private Integer index;

    private String icon;

}
