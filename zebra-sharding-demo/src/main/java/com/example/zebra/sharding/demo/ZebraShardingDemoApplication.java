package com.example.zebra.sharding.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = {"com.example.zebra.sharding.demo.druid.stat"})
@MapperScan("com.example.zebra.sharding.demo.mapper")
public class ZebraShardingDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZebraShardingDemoApplication.class, args);
    }

}
