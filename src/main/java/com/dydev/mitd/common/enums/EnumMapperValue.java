package com.dydev.mitd.common.enums;

import lombok.Getter;

@Getter
public class EnumMapperValue {

    private final String key;
    private final String value;

    public EnumMapperValue(EnumMapperType enumMapperType) {
        key = enumMapperType.getKey();
        value = enumMapperType.getValue();
    }

    @Override
    public String toString() {
        return "{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

}
