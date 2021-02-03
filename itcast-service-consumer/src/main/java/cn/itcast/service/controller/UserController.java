package cn.itcast.service.controller;

import cn.itcast.service.pojo.User;
//import com.netflix.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/1/8 11:15 上午
 */

@Controller
@RequestMapping("consumer/user")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;//包含了拉取的所有微服务地址

    //消费者访问地址 http://localhost:8080/consumer/user?id=28
    @GetMapping
    @ResponseBody
    public User queryUserById(@RequestParam("id") Long id) {
        //从Eureka获取服务实例
//        List<ServiceInstance> instances = discoveryClient.getInstances("service-provider");
//        ServiceInstance instance = instances.get(0);

//        填写生产者的url地址 id用28  硬编码地址
//        return this.restTemplate.getForObject("http://localhost:8081/user/" + id, User.class);

        //解决硬编码地址问题
//        return this.restTemplate.getForObject("http://" + instance.getHost() + ":" + instance.getPort() + "/user/" + id, User.class);

//        使用ribbon负载均衡的写法
        return this.restTemplate.getForObject("http://service-provider/user/" + id, User.class);

    }
}
