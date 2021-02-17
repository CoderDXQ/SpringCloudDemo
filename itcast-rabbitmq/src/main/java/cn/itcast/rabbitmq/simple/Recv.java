package cn.itcast.rabbitmq.simple;

import java.io.IOException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import cn.itcast.rabbitmq.util.ConnectionUtil;

/**
 * 消费者
 */
public class Recv {
    private final static String QUEUE_NAME = "simple_queue";

    public static void main(String[] argv) throws Exception {
        // 获取到连接
        Connection connection = ConnectionUtil.getConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 定义队列的消费者 匿名内部类
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            // 获取消息，并且处理，这个方法类似事件监听，如果有消息的时候，会被自动调用
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
                                       byte[] body) throws IOException {

//                制造异常
//                int i = 10 / 0;

                // body 即消息体
                String msg = new String(body);
                System.out.println(" [x] received : " + msg + "!");
            }
        };
//         监听队列，第二个参数：是否自动进行消息确认。  自动确认的参数设置为true后，上面的代码出现异常仍会使生产者消费message 应该手动确认避免这个问题
//        报错并不是出现在上面二是出现在这里    因为上面只是使用匿名内部类建立了一个对象 调用对象是在这里
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}