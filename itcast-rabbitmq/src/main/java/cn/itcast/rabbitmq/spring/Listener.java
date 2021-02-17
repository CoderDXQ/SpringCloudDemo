package cn.itcast.rabbitmq.spring;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: HuYi.Zhang
 * @create: 2018-05-23 18:04
 **/
@Component//把普通pojo实例化到容器
public class Listener {

    //    声明是一个监听器
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "spring.test.queue", durable = "true"),//队列名 可持久化
            exchange = @Exchange(
                    value = "spring.test.exchange",//交换机
                    ignoreDeclarationExceptions = "true",//忽略声明异常
                    type = ExchangeTypes.TOPIC//topic模式
            ),
            key = {"#.#"}))//通配符
    public void listen(String msg) {
        System.out.println("接收到消息：" + msg);
    }
}
