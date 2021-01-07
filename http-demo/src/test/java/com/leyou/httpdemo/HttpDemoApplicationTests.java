package com.leyou.httpdemo;

import com.leyou.httpdemo.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HttpDemoApplication.class)//声明是一个SpringBoot的测试程序 并说明引导类
public class HttpDemoApplicationTests {

	@Autowired//注入  这个类可以自动执行序列化和反序列化
	private RestTemplate restTemplate;

	@Test
	public void httpGet() {
		User user = this.restTemplate.getForObject("http://localhost:8080/user/28", User.class);
		System.out.println();
		System.out.println(user);
		System.out.println(user.toString());
	}

}
