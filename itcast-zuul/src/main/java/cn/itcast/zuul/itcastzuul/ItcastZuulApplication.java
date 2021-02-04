package cn.itcast.zuul.itcastzuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy//启用Zuul组件
public class ItcastZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItcastZuulApplication.class, args);
	}

}
