package com.leyou.httpdemo.service;

import com.leyou.httpdemo.mapper.UserMapper;
import com.leyou.httpdemo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/1/7 5:24 下午
 */

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User queryById(Long id) {
        return this.userMapper.selectByPrimaryKey(id);
    }
}
