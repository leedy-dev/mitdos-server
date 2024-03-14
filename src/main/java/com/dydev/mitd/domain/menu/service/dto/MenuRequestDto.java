package com.dydev.mitd.domain.menu.service.dto;

import com.dydev.mitd.domain.role.enums.RoleTypes;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MenuRequestDto extends MenuDto{

    @Getter
    @Setter
    public static class Search {

        private Long upperMenuId;

        private RoleTypes auth;

        private Integer level;

    }

}
