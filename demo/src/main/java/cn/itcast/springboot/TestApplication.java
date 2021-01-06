package cn.itcast.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/1/6 11:40 下午
 */
//引导类 SpringBoot的入口
@EnableAutoConfiguration //开启自动配置
@ComponentScan  //开启包扫描 递归向下扫描该类所在的包
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
