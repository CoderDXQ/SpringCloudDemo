package cn.itcast.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/1/8 11:11 上午
 */
//@SpringBootApplication
//@EnableDiscoveryClient//启动Eureka客户端
//@EnableCircuitBreaker//开启开启熔断器
@SpringCloudApplication //组合注解 是上面是三个的组合
@EnableFeignClients //启动Feign
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
