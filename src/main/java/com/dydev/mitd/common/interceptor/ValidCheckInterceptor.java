package com.dydev.mitd.common.interceptor;

import com.dydev.mitd.common.exception.ApiException;
import com.dydev.mitd.common.exception.ErrorMessage;
import com.dydev.mitd.common.constants.CommonConstants;
import com.dydev.mitd.common.utils.CommonObjectUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Set;

@Aspect
@Slf4j
@Component
public class ValidCheckInterceptor {

    @Around("execution(* com.dyprj.mitd..*.*Controller.*(..))")
    public Object anyMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] objs = joinPoint.getArgs();
        for(Object obj : objs){
            if(obj instanceof BindingResult bindingResult){
                if(bindingResult.hasErrors()) {
                    List<FieldError> fieldErrors = bindingResult.getFieldErrors();
                    StringBuilder errMsg = new StringBuilder();
                    for (FieldError fieldError : fieldErrors) {
                        errMsg.append(fieldError.getField()).append(CommonConstants.COLON).append(fieldError.getDefaultMessage()).append(CommonConstants.COMMA);
                    }
                    throw new ApiException(errMsg.toString(), ErrorMessage.INVALID_VALUE);
                }
            }
        }

        Object obj;

        try {
            obj = joinPoint.proceed(joinPoint.getArgs());
        } catch (RuntimeException e) {
            if(e instanceof ConstraintViolationException constraintViolationException) {

                if(CommonObjectUtils.isNotEmpty(constraintViolationException.getConstraintViolations())) {
                    Set<ConstraintViolation<?>> constraintViolations = constraintViolationException.getConstraintViolations();
                    StringBuilder errMsg = new StringBuilder();
                    for (ConstraintViolation<?> constraintViolation : constraintViolations) {
                        errMsg.append(constraintViolation.getPropertyPath()).append(CommonConstants.COLON).append(constraintViolation.getMessage()).append(CommonConstants.COMMA);
                    }
                    throw new ApiException(errMsg.toString(), ErrorMessage.INVALID_VALUE);
                }
            }
            throw e;
        }

        return obj;
    }

}
