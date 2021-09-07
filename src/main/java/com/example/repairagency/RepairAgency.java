package com.example.repairagency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

/**
 * The RepairAgencyProgramme for creation order and handling it.
 *
 * @author oksanamoysyuk
 * @version 1.0
 * @since 15.08.2021
 */
@SpringBootApplication
public class RepairAgency {

    @Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }

    public static void main(String[] args) {
        SpringApplication.run(RepairAgency.class, args);
    }

}
