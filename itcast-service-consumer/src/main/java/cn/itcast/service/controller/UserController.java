package cn.itcast.service.controller;

import cn.itcast.service.pojo.User;
//import com.netflix.discovery.DiscoveryClient;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 降级：熔断之后一段时间之后服务再次开启 默认时间是一秒
 * 熔断：closed：所有请求正常访问
 *      open：所有请求无法访问   可以设置开启条件
 *      half open：：打开状态有默认5秒的休眠期，休眠期之后进入半开状态会放部分请求通过
 *
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/1/8 11:15 上午
 */

@Controller
@RequestMapping("consumer/user")
@DefaultProperties(defaultFallback = "fallbackMethod")//定义全局的熔断降级返回方法 熔断方法和被熔断方法的返回值类型和参数方法是要一致的
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;//包含了拉取的所有微服务地址

    //消费者访问地址 http://localhost:8080/consumer/user?id=28


    /**
     * //    http://localhost:8080/consumer/user?id=28 可以使用这个地址
     *
     * @GetMapping
     * @ResponseBody //    @HystrixCommand(fallbackMethod = "queryUserByIdFallback") //调用失败触发熔断机制
     * public User queryUserById(@RequestParam("id") Long id) {
     * //从Eureka获取服务实例
     * //                List<ServiceInstance> instances = discoveryClient.getInstances("service-provider");
     * //                ServiceInstance instance = instances.get(0);
     * <p>
     * //        填写生产者的url地址 id用28  硬编码地址
     * //        return this.restTemplate.getForObject("http://localhost:8081/user/" + id, User.class);
     * <p>
     * //解决硬编码地址问题
     * //                return this.restTemplate.getForObject("http://" + instance.getHost() + ":" + instance.getPort() + "/user/" + id, User.class);
     * <p>
     * //        使用ribbon负载均衡的写法  地址是生产者的地址
     * return this.restTemplate.getForObject("http://service-provider/user/" + id, User.class);
     * }
     */

    //    @HystrixCommand(fallbackMethod = "queryUserByIdFallback") //调用失败触发熔断机制
    @GetMapping
    @ResponseBody
    @HystrixCommand
    public String queryUserById1(@RequestParam("id") Long id) throws InterruptedException {
        //从Eureka获取服务实例
//        List<ServiceInstance> instances = discoveryClient.getInstances("service-provider");
//        ServiceInstance instance = instances.get(0);

//        填写生产者的url地址 id用28  硬编码地址
//        return this.restTemplate.getForObject("http://localhost:8081/user/" + id, User.class);

        //解决硬编码地址问题
//        return this.restTemplate.getForObject("http://" + instance.getHost() + ":" + instance.getPort() + "/user/" + id, User.class);

        if (id == 1) {
//            抛出运行时异常
            throw new RuntimeException();
        }
//        Thread.sleep(10000);
//        使用ribbon负载均衡的写法
        return this.restTemplate.getForObject("http://service-provider/user/" + id, String.class);

    }

    //    这里面的"Long id"不能删除   @HystrixCommand(fallbackMethod = "")要求参数是一致的
//    public String fallbackMethod(Long id) {
//        return "服务器正忙，请稍后再试";
//    }


    //    全局配置的熔断器的方法的参数为空
    public String fallbackMethod() {
        return "服务器正忙，请稍后再试";
    }


}
