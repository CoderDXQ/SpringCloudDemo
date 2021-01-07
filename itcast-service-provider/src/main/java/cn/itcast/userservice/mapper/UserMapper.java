package cn.itcast.userservice.mapper;

import cn.itcast.userservice.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/1/7 5:24 下午
 */
@Mapper
public interface UserMapper extends tk.mybatis.mapper.common.Mapper<User> {
}