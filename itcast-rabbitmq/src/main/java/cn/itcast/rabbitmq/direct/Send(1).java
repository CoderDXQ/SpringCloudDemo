package cn.itcast.rabbitmq.direct;

import cn.itcast.rabbitmq.util.ConnectionUtil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 生产者，模拟为商品服务
 */

//路由模式  生产者可以指定消息的消费者
class Send {
    private final static String EXCHANGE_NAME = "direct_exchange_test";

    public static void main(String[] argv) throws Exception {
        // 获取到连接
        Connection connection = ConnectionUtil.getConnection();
        // 获取通道
        Channel channel = connection.createChannel();
        // 声明exchange，指定类型为direct 交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        // 消息内容
        String message = "商品删除了， id = 1001";
        // 发送消息，并且指定routing key 为：delete ,代表删除商品
        channel.basicPublish(EXCHANGE_NAME, "delete", null, message.getBytes());
        System.out.println(" [商品服务：] Sent '" + message + "'");

        // 消息内容
        String message1 = "商品新增了， id = 1002";
        // 发送消息，并且指定routing key 为：insert ,代表新增商品
        channel.basicPublish(EXCHANGE_NAME, "insert", null, message1.getBytes());
        System.out.println(" [商品服务：] Sent '" + message1 + "'");

        channel.close();
        connection.close();
    }
}