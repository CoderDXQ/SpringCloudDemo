package cn.itcast.service.client;

import cn.itcast.service.pojo.User;
import org.springframework.stereotype.Component;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/4 12:03 下午
 */

@Component //实例化到Spring容器
public class UserClientFallback implements UserClient {

    @Override
    public User queryById(Long id) {
        User user = new User();
        user.setUsername("服务器正忙，请稍后再试！");
        return user;
    }


}
