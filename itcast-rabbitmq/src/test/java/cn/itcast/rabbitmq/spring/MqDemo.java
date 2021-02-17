package cn.itcast.rabbitmq.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: HuYi.Zhang
 * @create: 2018-05-23 18:08
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MqDemo {

    @Autowired //加载AMQP模板
    private AmqpTemplate amqpTemplate;

    @Test
    public void testSend() throws InterruptedException {
        String msg = "hello, Spring boot amqp";
//        指定交换机、RoutingKey和消息体
        this.amqpTemplate.convertAndSend("spring.test.exchange", "a.b", msg);
        // 等待10秒后再结束
        Thread.sleep(10000);
    }
}
