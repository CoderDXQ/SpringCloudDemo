import cn.itcast.service.ItcastServiceConsumerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/4 2:09 上午
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItcastServiceConsumerApplication.class)
public class RibbonLoadBalanceTest {

    @Autowired
    private RibbonLoadBalancerClient client;

    @Test
    public void test() {
        for (int i = 0; i < 50; i++) {
//            选择微服务
            ServiceInstance instance = this.client.choose("service-provider");
            System.out.println(instance.getHost() + ":" + instance.getPort());
        }
    }
}
