package com.example.zebra.multisource.spring.boot.configure.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

/**
 * 描述:
 *
 * @author Administrator
 * @date 2019-06-11 11:52
 */
@ConfigurationProperties(prefix = "spring.zebra.multi")
@Getter
@Setter
public class ZebraMultiDataSourceProperties {

    private Properties props = new Properties();

}