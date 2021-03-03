package com.leyou.delayqueue2;

import cn.itcast.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;


/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/3/3 6:30 下午
 */

//这个消费者与message_ttl_queue队列相连
public class Consumer {

    private static String queue_name = "message_ttl_queue";

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(queue_name, true, false, false, null);
//        绑定交换机和路由
        channel.queueBind(queue_name, "amq.direct", "message_ttl_routingKey");

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

//                body就是消息体
                String msg = new String(body);
                System.out.println("[消费者] received : " + msg);
                Long result = System.currentTimeMillis() - Long.parseLong(msg);
                System.out.println("当期时间：" + System.currentTimeMillis());
                System.out.println("间隔：" + result / 1000);
            }
        };

        channel.basicConsume(queue_name, true, consumer);

    }
}
