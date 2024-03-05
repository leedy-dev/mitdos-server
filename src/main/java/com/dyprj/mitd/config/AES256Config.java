package com.dyprj.mitd.config;

import com.dyprj.mitd.common.utils.ErrorLogUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
public class AES256Config {

    private static String AES256_KEY;

    @PostConstruct
    public void init() {
        Resource resource = new ClassPathResource("aes256-key.txt");
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
            AES256_KEY = new String(bdata, StandardCharsets.UTF_8);
        } catch (IOException ie) {
            ErrorLogUtils.printError(log, ie, "aes256-key Read Error!");
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ie) {
                    ErrorLogUtils.printError(log, ie);
                }
            }
        }
    }

    public static String getAES256_KEY() {
        return AES256_KEY;
    }

}
