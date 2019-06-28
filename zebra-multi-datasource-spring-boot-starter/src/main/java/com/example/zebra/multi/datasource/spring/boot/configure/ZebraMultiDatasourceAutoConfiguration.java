package com.example.zebra.multi.datasource.spring.boot.configure;

import com.dianping.zebra.dao.datasource.ZebraRoutingDataSource;
import com.example.zebra.multi.datasource.spring.boot.configure.exception.ShardingException;
import com.example.zebra.multi.datasource.spring.boot.configure.properties.ZebraMultiMybatisProperties;
import com.example.zebra.multi.datasource.spring.boot.configure.util.DataSourceUtil;
import com.example.zebra.multi.datasource.spring.boot.configure.util.InlineExpressionParser;
import com.example.zebra.multi.datasource.spring.boot.configure.util.PropertyUtil;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.*;

/**
 * 描述:
 *
 * @author qianyue
 * @date 2019-06-27 17:58
 */
@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties({
        ZebraMultiMybatisProperties.class})
@RequiredArgsConstructor
public class ZebraMultiDatasourceAutoConfiguration implements EnvironmentAware {

    private Logger logger = LoggerFactory.getLogger(ZebraMultiDatasourceAutoConfiguration.class);

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
        String prefix = "zebra.multi.datasource.";
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
        return null == standardEnv.getProperty(prefix + "name")
                ? new InlineExpressionParser(standardEnv.getProperty(prefix + "names")).splitAndEvaluate() : Collections.singletonList(standardEnv.getProperty(prefix + "name"));

    }
}