package com.dydev.mitd.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorMessage implements ErrorMessageIF {
    // AUTH
    TOKEN_WITHOUT_CREDENTIAL(400, "Token without credential"),

    // TOKEN
    ACCESS_TOKEN_NOT_FOUND(400, "Access token not found"),
    REFRESH_TOKEN_NOT_FOUND(400, "Refresh token not found"),
    INVALID_REFRESH_TOKEN(400, "Invalid refresh token"),
    EXPIRED_TOKEN_REFRESH_TOKEN(400, "Expired refresh token"),

    // RESOURCE
    RESOURCE_NOT_FOUND(204, "Resource not found"),

    // DATA
    DATA_EXISTS(400, "Data that already exists"),
    DATA_NOT_FOUND(400, "Data not found"),
    INVALID_VALUE(400, "Invalid value"),

    // USER
    LOGIN_USER_NOT_FOUND(400, "Login user not found"),
    USER_NOT_FOUND(400, "User not found"),
    DUPLICATE_USER_ID(400, "This is a duplicate User Id");

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
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public int getStatus() {
        return 0;
    }

    @Override
    public String getDetail() {
        return null;
    }

    @Override
    public String getUuid() {
        return null;
    }
}
