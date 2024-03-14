package com.dydev.mitd.common.exception.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorMessage implements ErrorMessageIF {
    // AUTH
    TOKEN_WITHOUT_CREDENTIAL(400, "Token Without Credential"),
    UNAUTHORIZED_USER(401, "Unauthorized User"),
    ACCESS_DENIED(403, "Access Denied"),

    // TOKEN
    ACCESS_TOKEN_NOT_FOUND(400, "Access Token Not Found"),
    REFRESH_TOKEN_NOT_FOUND(400, "Refresh Token Not Found"),
    INVALID_REFRESH_TOKEN(400, "Invalid Refresh Token"),
    EXPIRED_TOKEN_REFRESH_TOKEN(400, "Expired Refresh Token"),

    // RESOURCE
    RESOURCE_NOT_FOUND(204, "Resource Not Found"),
    TEMPORARY_SERVER_ERROR(400, "Temporary Server Error"),

    // DATA
    DATA_EXISTS(400, "Data That Already Exists"),
    DATA_NOT_FOUND(400, "Data Not Found"),
    INVALID_VALUE(400, "Invalid Value"),

    // USER
    LOGIN_USER_NOT_FOUND(400, "Login User Not Found"),
    USER_NOT_FOUND(400, "User Not Found"),
    DUPLICATE_USER_ID(400, "Duplicated User Id"),
    DUPLICATE_EMAIL(400, "Duplicated Email"),

    // TEST
    ERROR_MESSAGE_TEST(400, "Greetings");

    private int status;
    private String message;
    private String detail;
    private String uuid;

    ErrorMessage(int status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getDetail() {
        return detail;
    }

    @Override
    public String getUuid() {
        return uuid;
    }
}
