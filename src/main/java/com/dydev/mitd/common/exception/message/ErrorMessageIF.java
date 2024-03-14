package com.dydev.mitd.common.exception.message;

public interface ErrorMessageIF {
    String getCode();
    String getMessage();
    int getStatus();
    String getDetail();
    String getUuid();
}
