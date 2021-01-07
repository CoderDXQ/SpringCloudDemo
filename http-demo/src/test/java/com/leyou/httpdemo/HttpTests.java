package com.leyou.httpdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leyou.httpdemo.pojo.User;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class HttpTests {

    CloseableHttpClient httpClient;

    //    有序列化和反序列化的方法
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Before
//    初始化  相当于打开一个浏览器
    public void init() {
        httpClient = HttpClients.createDefault();
    }

    @Test
    public void testGet() throws IOException {
//        HttpGet request = new HttpGet("http://www.baidu.com");
//        执行前需要先启动
        HttpGet request = new HttpGet("http://localhost:8080/hello2/show");
        String response = this.httpClient.execute(request, new BasicResponseHandler());
//        输出整个页面的源码
        System.out.println(response);
    }

    @Test
    public void testPost() throws IOException {
        HttpPost request = new HttpPost("https://www.oschina.net/");
        request.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        String response = this.httpClient.execute(request, new BasicResponseHandler());
        System.out.println(response);
    }

    @Test
    public void testGetPojo() throws IOException {
        HttpGet request = new HttpGet("http://localhost/hello");
        String response = this.httpClient.execute(request, new BasicResponseHandler());
        System.out.println(response);

//        反序列化
        User user = MAPPER.readValue(response, User.class);
        System.out.println(user);
    }

    //    json处理工具
//    private ObjectMapper mapper = new ObjectMapper();
    @Test
    public void testJson() throws IOException {
        User user = new User();
        user.setId(8L);
        user.setAge(21);
        user.setName("柳岩");
        user.setUserName("liuyan");
//        打印类信息
        System.out.println(user);
        // 序列化
        String json = MAPPER.writeValueAsString(user);
        System.out.println("json = " + json);
//        反序列化
        User user1 = MAPPER.readValue(json, User.class);
        System.out.println(user1);
    }
}
