package com.dydev.mitd.domain.role.enums;

import com.dydev.mitd.common.annotation.EnumFindable;
import com.dydev.mitd.common.enums.EnumMapperType;

@EnumFindable
public enum RoleTypes implements EnumMapperType {
    ROLE_USER("사용자"),
    ROLE_ADMIN("관리자"),
    ROLE_SUPER("슈퍼관리자");

    private String value;

    RoleTypes(String value) {
        this.value = value;
    }

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return value;
    }

}
