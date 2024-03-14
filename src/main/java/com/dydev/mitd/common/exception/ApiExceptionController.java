package com.dydev.mitd.common.exception;

import com.dydev.mitd.common.constants.CommonApiUrls;
import com.dydev.mitd.common.exception.exception.ApiException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CommonApiUrls.API_PACKAGE_EXCEPTION)
public class ApiExceptionController {

    @GetMapping("/entry-point")
    public void doExceptionEntryPoint() {
        throw new ApiException.AuthenticationEntryPointException();
    }

    @GetMapping("/access-denied")
    public void doExceptionAccessDenied() {
        throw new ApiException.AccessDeniedException();
    }

}
