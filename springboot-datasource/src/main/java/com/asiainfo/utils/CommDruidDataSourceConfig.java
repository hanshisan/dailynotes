package com.asiainfo.utils;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author hansong
 * @ClassName CommDruidDataSourceConfig
 * @create at  2019-07-16 16:34
 * @desc
 * @Version 1.0
 **/
@Configuration
@MapperScan(basePackages = {"com.asiainfo.comm.**.dao"},sqlSessionFactoryRef = "commSqlSessionFactory")
public class CommDruidDataSourceConfig {

    @Value("${spring.datasource.comm.commMapperLocations}")
    private String commMapperLocations ;

    @Value("${spring.datasource.comm.configLocation}")
    private String configLocation;

    @ConfigurationProperties(prefix = "spring.datasource.comm")

    @Bean(name = "commDataSource")
    @Primary
    public DataSource commDataSource() {
        return new DruidDataSource();
    }

    @Bean("commJdbcTemplate")
    @Primary
    public JdbcTemplate template(@Qualifier(value = "commDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


    /**
     * 自动识别使用的数据库类型
     * 在mapper.xml中databaseId的值就是跟这里对应，
     * 如果没有databaseId选择则说明该sql适用所有数据库
     * */
    @Bean(name = "commDatabaseIdProvider")
    @Primary
    public DatabaseIdProvider getDatabaseIdProvider() {
        DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        Properties properties = new Properties();
        properties.setProperty("Oracle", "oracle");
        properties.setProperty("MySQL", "mysql");
        properties.setProperty("H2", "h2");
        databaseIdProvider.setProperties(properties);
        return databaseIdProvider;
    }

    /**
     * SqlSessionFactory配置
     *
     * @return
     * @throws Exception
     */
    @Bean(name = "commSqlSessionFactory")
    @Primary
    public SqlSessionFactory commSqlSessionFactory(@Qualifier("commDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        //自动识别 支持多种数据库关键代码
        sqlSessionFactoryBean.setDatabaseIdProvider(getDatabaseIdProvider());
        // 配置mapper文件位置
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(commMapperLocations));
        sqlSessionFactoryBean.setConfigLocation(resolver.getResource(configLocation));
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 配置事物管理器
     *
     * @return
     */
    @Bean(name = "commTransactionManager")
    @Primary
    public DataSourceTransactionManager commTransactionManager(@Qualifier("commDataSource") DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }
}