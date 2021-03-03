package com.leyou.delayqueue;

import cn.itcast.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;


/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/3/3 6:29 下午
 */
public class Consumer {

    private final static String QUEUE_NAME = "test.queue";

    public static void main(String[] args) throws Exception {

//        获取连接
        Connection connection = ConnectionUtil.getConnection();

//        获取通道
        Channel channel = connection.createChannel();

//        声明队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

//        定义队列的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                body就是消息体
                String msg = new String(body);
                System.out.println("[消费者] received : " + msg);
                Long result = System.currentTimeMillis() - Long.parseLong(msg);
                System.out.println("当前时间：" + System.currentTimeMillis());
                System.out.println("间隔： " + result / 1000);
            }
        };
//        通过通道消费消息
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
