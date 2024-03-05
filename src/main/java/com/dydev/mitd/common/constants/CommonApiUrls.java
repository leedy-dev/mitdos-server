package com.dydev.mitd.common.constants;

public class CommonApiUrls {

    private CommonApiUrls() {
        throw new IllegalStateException("Utility class");
    }

    public static final String PROJECT_NAME     = "/dy-dev";

    public static final String API_PREFIX       = "/api";

    public static final String PROJECT_API_PREFIX   = PROJECT_NAME + API_PREFIX;

    // CONSTANTS
    public static final String API_PACKAGE_AUTH = PROJECT_API_PREFIX + "/auth";
    public static final String API_PACKAGE_USER = PROJECT_API_PREFIX + "/user";

}
