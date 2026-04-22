package com.ecom.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.util.TimeZone;

@SpringBootApplication
public class ProductApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata")); // ✅ FIX
        SpringApplication.run(ProductApplication.class, args);

    }

}
