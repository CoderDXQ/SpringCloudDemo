package cn.itcast.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
//@MapperScan("cn.itcast.userservice.mapper")
@EnableDiscoveryClient //开启Eureka客户端功能
public class UserServiceProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceProviderApplication.class, args);
	}

}
