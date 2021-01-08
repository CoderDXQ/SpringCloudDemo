package cn.itcast.service.controller;

import cn.itcast.service.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

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

    //消费者访问地址 http://localhost:8080/consumer/user?id=28
    @GetMapping
    @ResponseBody
    public User queryUserById(@RequestParam("id") Long id) {
//        填写生产者的url地址 id用28
        return this.restTemplate.getForObject("http://localhost:8081/user/" + id, User.class);

    }
}
