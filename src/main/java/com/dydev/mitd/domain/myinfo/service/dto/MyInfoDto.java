package com.dydev.mitd.domain.myinfo.service.dto;

import com.dydev.mitd.common.base.dto.BaseCUDto;
import com.dydev.mitd.common.validate.PatternDefine;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyInfoDto extends BaseCUDto {

    @NotEmpty
    @Size(max = 10)
    private String nameKor;

    @NotEmpty
    @Size(max = 10)
    private String nameEng;

    @NotEmpty
    @Pattern(regexp = PatternDefine.MOBILE_PHONE_NUMBER_PATTERN)
    @Size(max = 30)
    private String phoneNum;

    @NotEmpty
    @Pattern(regexp = PatternDefine.EMAIL_PATTERN)
    @Size(max = 200)
    private String email;

    @NotEmpty
    @Size(max = 200)
    private String notice;

    @NotEmpty
    @Size(max = 40000)
    private String introduction;

}
