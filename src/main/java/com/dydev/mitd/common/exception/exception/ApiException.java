package com.dydev.mitd.common.exception.exception;

import com.dydev.mitd.common.exception.message.ErrorMessageIF;

public class ApiException extends RuntimeException {

    private final ErrorMessageIF errorMessage;
    private final Object[] errorMessageArgs;

    public ApiException(String message, ErrorMessageIF errorMessage, Object[] errorMessageArgs) {
        super(message);
        this.errorMessage = errorMessage;
        this.errorMessageArgs = errorMessageArgs;
    }

    public ApiException(String message, ErrorMessageIF errorMessage) {
        super(message);
        this.errorMessage = errorMessage;
        this.errorMessageArgs = null;
    }

    public ApiException(ErrorMessageIF errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
        this.errorMessageArgs = null;
    }

    public ErrorMessageIF getErrorCode() {
        return this.errorMessage;
    }

    public Object[] getErrorMessageArgs() {
        return this.errorMessageArgs;
    }

    // 401 - entry point
    public static class AuthenticationEntryPointException extends RuntimeException {}

    // 403 - access denied
    public static class AccessDeniedException extends RuntimeException {}

}
