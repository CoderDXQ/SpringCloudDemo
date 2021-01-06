package cn.itcast.springboot.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/1/7 12:02 上午
 */

@Configuration //声明一个类是配置类
//@PropertySource("classpath:jdbc.properties") //读取资源文件
@EnableConfigurationProperties(JdbcProperties.class) //启动配置类
public class JdbcConfiguration {

//    @Value("${jdbc.driverClassName}")
//    private String driverClassName;
//    @Value("${jdbc.url}")
//    private String url;
//    @Value("${jdbc.username}")
//    private String username;
//    @Value("${jdbc.password}")
//    private String password;

    @Autowired
    private JdbcProperties jdbcProperties;

    public JdbcConfiguration(JdbcProperties jdbcProperties) {
        this.jdbcProperties = jdbcProperties;
    }

    //    java配置类
    @Bean //把方法的返回值注入到Spring容器中
    @ConfigurationProperties(prefix = "jdbc")
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setDriverClassName(this.driverClassName);
//        dataSource.setUrl(this.url);
//        dataSource.setUsername(this.username);
//        dataSource.setPassword(this.password);


//        dataSource.setDriverClassName(this.jdbcProperties.getDriverClassName());
//        dataSource.setUrl(this.jdbcProperties.getUrl());
//        dataSource.setUsername(this.jdbcProperties.getUsername());
//        dataSource.setPassword(this.jdbcProperties.getPassword());
        return dataSource;
    }
}
