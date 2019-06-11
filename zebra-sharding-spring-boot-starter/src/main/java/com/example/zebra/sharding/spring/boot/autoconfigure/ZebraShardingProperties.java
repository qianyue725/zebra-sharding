package com.example.zebra.sharding.spring.boot.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 描述:
 *
 * @author Administrator
 * @date 2019-06-06 15:14
 */
@Setter
@Getter
@ConfigurationProperties("spring.zebra.sharding")
public class ZebraShardingProperties {

    private String configManagerType = "local";

    private String poolType = "druid";

    private Integer parallelCorePoolSize = 32;

    private Integer parallelMaxPoolSize = 64;

    private Integer parallelWorkQueueSize = 500;

    private Integer parallelExecuteTimeOut = 1000;

    private String shardingRuleFile;

    private String dbSimpleName;
}