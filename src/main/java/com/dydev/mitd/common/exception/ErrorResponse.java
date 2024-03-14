package com.dydev.mitd.common.exception;

import com.dydev.mitd.common.exception.message.ErrorMessageIF;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {

    private String message;
    private String code;
    private int status;
    private String detail;
    private String uuid;

    public ErrorResponse(ErrorMessageIF errMsg) {
        this.message = errMsg.getMessage();
        this.status = errMsg.getStatus();
        this.code = errMsg.getCode();
        this.detail = errMsg.getDetail();
        this.uuid = errMsg.getUuid();
    }

    public static ErrorResponse of(ErrorMessageIF errorMessage) {
        return new ErrorResponse(errorMessage);
    }

}
