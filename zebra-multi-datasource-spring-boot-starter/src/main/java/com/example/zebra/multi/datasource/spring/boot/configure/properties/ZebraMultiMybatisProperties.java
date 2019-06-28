package com.example.zebra.multi.datasource.spring.boot.configure.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 描述:
 *
 * @author Administrator
 * @date 2019-06-10 19:52
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "zebra.multi.mybatis")
public class ZebraMultiMybatisProperties {

    private String mapperLocations;

    private String typeAliasesPackage;
}