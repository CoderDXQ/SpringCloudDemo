package cn.itcast.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/1/8 11:11 上午
 */
@SpringBootApplication
@EnableDiscoveryClient//启动Eureka客户端
public class ItcastServiceConsumerApplication {

    @Bean
    @LoadBalanced //开启ribbon的负载均衡
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(ItcastServiceConsumerApplication.class, args);
    }
}
