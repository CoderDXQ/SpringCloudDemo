package cn.itcast.springboot.controller;

import com.alibaba.druid.support.json.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.ls.LSOutput;

import javax.sql.DataSource;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/1/6 9:25 下午
 */

@RestController
@RequestMapping("hello")
//@EnableAutoConfiguration //启动自动配置
public class HelloController {

    @Autowired
    private DataSource dataSource;


    @GetMapping("show")
    public String test() {
//        System.out.println(dataSource.toString());
        return "hello springboot 1";
    }

//    public static void main(String[] args) {
////        使用springboot启动某个类
//        SpringApplication.run(HelloController.class, args);
//    }
}
