package com.leyou.httpdemo.controller;


import com.leyou.httpdemo.pojo.User;
import com.leyou.httpdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/1/7 5:23 下午
 */

//数据库的表要提前建好，不然拿不到数据
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public User queryById(@PathVariable("id") Long id) {
        return this.userService.queryById(id);
    }
}