package com.dydev.mitd.common.utils;

import com.dydev.mitd.common.provider.ApplicationContextProvider;
import org.springframework.context.ApplicationContext;

public class CommonBeanUtils {

    private CommonBeanUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Object getBean(String beanName) {
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
        return applicationContext.getBean(beanName);
    }

}
