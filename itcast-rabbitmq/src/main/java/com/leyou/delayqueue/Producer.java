package com.leyou.delayqueue;

import cn.itcast.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/3/3 6:30 下午
 */
public class Producer {

    //    发送消息后消息到达交换机，然后交换机把消息放进与其绑定的队列中
    private final static String EXCHANGE_NAME = "delaysync.exchange";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        String message = System.currentTimeMillis() + "";

        channel.basicPublish(EXCHANGE_NAME, "deal.message", null, message.getBytes());

        System.out.println("sent message: " + message + " ,date: " + System.currentTimeMillis());

        channel.close();
        connection.close();

    }
}
