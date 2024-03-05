package com.dydev.mitd.common.exception;

public interface ErrorMessageIF {
    String getCode();
    String getMessage();
    int getStatus();
    String getDetail();
    String getUuid();
}
