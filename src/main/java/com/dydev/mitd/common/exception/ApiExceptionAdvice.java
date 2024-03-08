package com.dydev.mitd.common.exception;

import com.dydev.mitd.common.utils.ErrorLogUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;
import java.util.UUID;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ApiExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorLogUtils.printError(log, e, "Exception Occurred");

        ErrorResponse response = ErrorResponse.of(ErrorMessage.TEMPORARY_SERVER_ERROR);
        response.setDetail(e.getMessage());
        response.setUuid(UUID.randomUUID().toString());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    protected ResponseEntity<ErrorResponse> handleNoSuchElementException(Exception e) {
        ErrorResponse response = ErrorResponse.of(ErrorMessage.RESOURCE_NOT_FOUND);
        response.setDetail(e.getMessage());
        response.setUuid(UUID.randomUUID().toString());

        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(value = ApiException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponse> handleApiException(ApiException e) {
        ErrorResponse response = ErrorResponse.of(e.getErrorCode());

        response.setCode(e.getErrorCode().getCode());
        response.setDetail(e.getMessage());
        response.setUuid(UUID.randomUUID().toString());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
