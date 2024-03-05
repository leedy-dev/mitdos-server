package com.dyprj.mitd.domain.user.enums;

import com.dyprj.mitd.common.annotation.EnumFindable;
import com.dyprj.mitd.common.enums.EnumMapperType;

@EnumFindable
public enum UserType implements EnumMapperType {
    SUPER("SUPER"),
    ADMIN("ADMIN"),
    USER("USER");

    private String value;

    UserType(String value) {
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
