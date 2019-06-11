package com.example.zebra.multisource.spring.boot.configure;

import com.dianping.zebra.dao.datasource.ZebraRoutingDataSource;
import com.example.zebra.multisource.spring.boot.configure.exception.ShardingException;
import com.example.zebra.multisource.spring.boot.configure.properties.ZebraMultiDataSourceProperties;
import com.example.zebra.multisource.spring.boot.configure.properties.ZebraMultiMybatisProperties;
import com.example.zebra.multisource.spring.boot.configure.util.DataSourceUtil;
import com.example.zebra.multisource.spring.boot.configure.util.PropertyUtil;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 *
 * @author Administrator
 * @date 2019-06-06 15:12
 */
@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties({ZebraMultiMybatisProperties.class, ZebraMultiDataSourceProperties.class})
@ConditionalOnProperty(prefix = "spring.zebra.multi", name = "enabled", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
public class ZebraMultiSourceAutoConfiguration implements EnvironmentAware {

    private Logger logger = LoggerFactory.getLogger(ZebraMultiSourceAutoConfiguration.class);

    private final ZebraMultiMybatisProperties zebraMultiMybatisProperties;

    private final Map<String, DataSource> dataSourcePool = new LinkedHashMap<>();


    @Bean(name = "routingDataSource")
    @Primary
    public ZebraRoutingDataSource routingDataSource() {
        ZebraRoutingDataSource routingDataSource = new ZebraRoutingDataSource();
        routingDataSource.setTargetDataSources(dataSourcePool);
        logger.info("ZebraRoutingDataSource Info: {}", routingDataSource);
        return routingDataSource;
    }

    @Bean(name="zebraMultiSqlSessionFactory")
    @Primary
    public SqlSessionFactoryBean sqlSessionFactory(@Qualifier("routingDataSource") ZebraRoutingDataSource routingDataSource) throws Exception {
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(routingDataSource);
        Configuration configuration = new Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        sqlSessionFactoryBean.setConfiguration(configuration);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(this.zebraMultiMybatisProperties.getMapperLocations()));
        if (StringUtils.hasLength(this.zebraMultiMybatisProperties.getTypeAliasesPackage())) {
            sqlSessionFactoryBean.setTypeAliasesPackage(this.zebraMultiMybatisProperties.getTypeAliasesPackage());
        }
        return sqlSessionFactoryBean;
    }

    @Override
    public void setEnvironment(Environment environment) {
        String prefix = "spring.zebra.multi.datasource.";
        for (String each : getDataSourceNames(environment, prefix)) {
            try {
                dataSourcePool.put(each, getDataSource(environment, prefix, each));
            } catch (final ReflectiveOperationException ex) {
                throw new ShardingException("Can't find datasource type!", ex);
            }
        }
        logger.info("RoutingDataSourcePool Info: {}", dataSourcePool);
    }

    @SuppressWarnings("unchecked")
    private DataSource getDataSource(final Environment environment, final String prefix, final String dataSourceName)
            throws ReflectiveOperationException {
        Map<String, Object> dataSourceProps = PropertyUtil.handle(environment, prefix + dataSourceName, Map.class);
        Preconditions.checkState(!dataSourceProps.isEmpty(), String.format("Wrong datasource [%s] properties!", dataSourceName));
        return DataSourceUtil.getDataSource(dataSourceProps.get("type").toString(), dataSourceProps);
    }


    private List<String> getDataSourceNames(final Environment environment, final String prefix) {
        StandardEnvironment standardEnv = (StandardEnvironment) environment;
        standardEnv.setIgnoreUnresolvableNestedPlaceholders(true);
        String dataSources = standardEnv.getProperty(prefix + "names");
        if (StringUtils.isEmpty(dataSources)) {
            return Collections.emptyList();
        }
        logger.info("DataSourceNames Info: {}", dataSources);
        return Lists.newArrayList(Splitter.on(',').trimResults().omitEmptyStrings().split(dataSources));
    }
}