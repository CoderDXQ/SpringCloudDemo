package cn.itcast.service.client;

import cn.itcast.service.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/4 11:47 上午
 */

@FeignClient(value = "service-provider",fallback = UserClientFallback.class)
public interface UserClient {

    @GetMapping("user/{id}")
    public User queryById(@PathVariable("id") Long id);
}
