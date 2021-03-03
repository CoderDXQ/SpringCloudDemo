package com.leyou.delayqueue2;

import cn.itcast.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/3/3 6:30 下午
 */

//延时队列  生产者发送消息到队列之后，消息不被消费的话经过ttl就会被延时交换机路由到新的队列 如果这个队列的消费者消费了就行了
public class Producer {

    private final static String QUEUE_NAME = "message_ttl_queue";

    public static void main(String[] args) throws Exception {

//        获取连接
        Connection connection = ConnectionUtil.getConnection();
//        获取通道
        Channel channel = connection.createChannel();
//        声明队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
//        绑定路由 交换机是amq.direct
        channel.queueBind(QUEUE_NAME, "amq.direct", "message_ttl_routingKey");

        String message = System.currentTimeMillis() + "";

//        设置延时属性 deliveryMode()方法用于设置持久性 non-persistent(1) persistent(2)
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
//        设置的ttl是60秒
        AMQP.BasicProperties properties = builder.expiration("6000").deliveryMode(2).build();

//        发布消息并通过路由转发 先发送到delay_queue,在这里面等待消费 如果未被消费就会被交换机路由到message_ttl_queue队列等待消费
//        确保delay_queue没有消费者才能出现延时效果
        channel.basicPublish("", "delay_queue", properties, message.getBytes());

//        如果直接发送到QUEUE_NAME = "message_ttl_queue"队列就会直接被消费 延时效果就没有了
//        channel.basicPublish("", QUEUE_NAME, properties, message.getBytes());

        System.out.println("sent message: " + message + " ,data: " + System.currentTimeMillis());

//        关闭通道和连接
        channel.close();
        connection.close();
    }

}
