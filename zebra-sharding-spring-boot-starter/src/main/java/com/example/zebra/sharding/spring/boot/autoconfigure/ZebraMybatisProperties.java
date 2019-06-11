package com.example.zebra.sharding.spring.boot.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 描述:
 *
 * @author Administrator
 * @date 2019-06-09 20:29
 */
@Setter
@Getter
@ConfigurationProperties("zebra.mybatis")
public class ZebraMybatisProperties {

    private String mapperLocations;

    private String typeAliasesPackage;

}