package com.dydev.mitd.domain.myinfo.controller;

import com.dydev.mitd.common.constants.CommonApiUrls;
import com.dydev.mitd.domain.myinfo.service.MyInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(CommonApiUrls.API_PACKAGE_MY_INFO)
public class MyInfoController {

    private final MyInfoService myInfoService;

}
