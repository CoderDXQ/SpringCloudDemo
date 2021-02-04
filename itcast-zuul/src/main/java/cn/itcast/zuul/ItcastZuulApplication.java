package cn.itcast.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy//启用Zuul组件
@EnableDiscoveryClient//启动Eureka发现
public class ItcastZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItcastZuulApplication.class, args);
	}

}
