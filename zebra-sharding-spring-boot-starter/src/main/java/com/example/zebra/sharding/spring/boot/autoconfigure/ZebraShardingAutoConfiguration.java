package com.example.zebra.sharding.spring.boot.autoconfigure;

import com.dianping.zebra.shard.jdbc.ShardDataSource;
import com.dianping.zebra.shard.router.builder.XmlResourceRouterBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 描述:
 *
 * @author Administrator
 * @date 2019-06-06 15:12
 */
@Configuration
@EnableConfigurationProperties({ZebraShardingProperties.class, ZebraMybatisProperties.class})
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class ZebraShardingAutoConfiguration {

    private final ZebraShardingProperties zebraShardingProperties;

    private final ZebraMybatisProperties zebraMybatisProperties;

    public ZebraShardingAutoConfiguration(ZebraShardingProperties zebraShardingProperties, ZebraMybatisProperties zebraMybatisProperties) {
        this.zebraShardingProperties = zebraShardingProperties;
        this.zebraMybatisProperties = zebraMybatisProperties;
    }

    @Bean
    public ShardDataSource shardDataSource(DataSource dataSource) {
        ShardDataSource shardDataSource = new ShardDataSource();
        shardDataSource.setPoolType(zebraShardingProperties.getPoolType());
        Map<String, DataSource> pool = new LinkedHashMap<>(1);
        pool.put(zebraShardingProperties.getDbSimpleName(), dataSource);
        shardDataSource.setDataSourcePool(pool);
        shardDataSource.setRouterFactory(new XmlResourceRouterBuilder(zebraShardingProperties.getShardingRuleFile()));
        shardDataSource.init();
        return shardDataSource;
    }

    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory(ShardDataSource shardDataSource) throws Exception {
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(shardDataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            sqlSessionFactoryBean.setMapperLocations(resolver.getResources(this.zebraMybatisProperties.getMapperLocations()));
        if (StringUtils.hasLength(this.zebraMybatisProperties.getTypeAliasesPackage())) {
            sqlSessionFactoryBean.setTypeAliasesPackage(this.zebraMybatisProperties.getTypeAliasesPackage());
        }
        return sqlSessionFactoryBean.getObject();
    }

}