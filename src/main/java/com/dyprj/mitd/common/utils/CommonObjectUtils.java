package com.dyprj.mitd.common.utils;

import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

public class CommonObjectUtils {

    private CommonObjectUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isNull(@Nullable Object obj){
        return Objects.isNull(obj);
    }

    public static boolean nonNull(@Nullable Object obj) {
        return Objects.nonNull(obj);
    }

    public static boolean isEmpty(@Nullable Object obj) {
        return ObjectUtils.isEmpty(obj);
    }

    public static boolean isNotEmpty(@Nullable Object obj) {
        return !ObjectUtils.isEmpty(obj);
    }

}
