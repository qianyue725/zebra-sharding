package com.example.zebra.multisource.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 描述:
 *
 * @author Administrator
 * @date 2019-06-10 21:46
 */
@SpringBootApplication
@MapperScan("com.example.zebra.multisource.demo.mapper")
public class ZebraMultiDataSourceDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZebraMultiDataSourceDemoApplication.class, args);
    }

}