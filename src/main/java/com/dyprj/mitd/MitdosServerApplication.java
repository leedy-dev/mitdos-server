package com.dyprj.mitd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan
public class MitdosServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MitdosServerApplication.class, args);
    }

}
