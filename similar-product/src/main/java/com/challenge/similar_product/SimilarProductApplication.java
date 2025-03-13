package com.challenge.similar_product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.challenge.similar_product.client")
public class SimilarProductApplication {
    private static final Logger LOG = LoggerFactory.getLogger(SimilarProductApplication.class);

    public static void main(String[] args) {
        LOG.info("Starting SimilarProducts application...");
        SpringApplication.run(SimilarProductApplication.class, args);
        LOG.info("SimilarProducts application started successfully!");
    }


}
