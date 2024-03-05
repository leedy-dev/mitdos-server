package com.dyprj.mitd.common.validate;

public class PatternDefine {

    private PatternDefine() {
        throw new IllegalStateException("Utility class");
    }

    public static final String MOBILE_PHONE_NUMBER_PATTERN = "^\\d{3}\\d{3,4}\\d{4}$";
    public static final String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@(?:[A-Za-z0-9-]+\\.)+[A-Za-z]{2,6}$";

    //public static final String PASSWORD_NUMBER_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[`~!@#$%^&*()\\-_=+])[A-Za-z\\d`~!@#$%^&*()\\-_=+]{8,20}$"; //영문, 숫자, 특수문자 필수 포함
    public static final String PASSWORD_NUMBER_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d`~!@#$%^&*()\\-_=+]{8,20}$"; //영문, 숫자 필수 포함

}
