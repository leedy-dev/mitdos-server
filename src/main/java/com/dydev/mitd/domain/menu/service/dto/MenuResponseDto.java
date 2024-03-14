package com.dydev.mitd.domain.menu.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class MenuResponseDto extends MenuDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long menuId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<MenuResponseDto> subMenus;

}
