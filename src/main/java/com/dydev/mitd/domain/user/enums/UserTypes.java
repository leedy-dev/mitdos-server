package com.dydev.mitd.domain.user.enums;

import com.dydev.mitd.common.enums.EnumMapperType;
import com.dydev.mitd.common.annotation.EnumFindable;

@EnumFindable
public enum UserTypes implements EnumMapperType {
    SUPER("SUPER"),
    ADMIN("ADMIN"),
    USER("USER");

    private String value;

    UserTypes(String value) {
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
