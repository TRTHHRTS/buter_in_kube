package com.trthhrts.orderinkube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class BillingInKubeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingInKubeApplication.class, args);
    }

}
