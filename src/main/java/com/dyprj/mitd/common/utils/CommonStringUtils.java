package com.dyprj.mitd.common.utils;

import org.springframework.util.StringUtils;

public class CommonStringUtils {

    private CommonStringUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean hasText(String str) {
        return StringUtils.hasText(str);
    }

    public static boolean nonHasText(String str) {
        return !StringUtils.hasText(str);
    }

}
