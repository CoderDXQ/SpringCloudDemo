package com.leyou.httpdemo;


import com.fasterxml.jackson.core.type.TypeReference;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
        HttpGet request = new HttpGet("http://localhost:8080/hello/show");
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
//        28号数据库里有，其他的不一定有
        HttpGet request = new HttpGet("http://localhost:8080/user/28");
        String response = this.httpClient.execute(request, new BasicResponseHandler());
        System.out.println(response);
        System.out.println();

//        反序列化
        User user = MAPPER.readValue(response, User.class);
        System.out.println(user.toString());
    }

    //    json处理工具 各种序列化与反序列化
//    private ObjectMapper mapper = new ObjectMapper();
    @Test
    public void testJson() throws IOException {
        User user = new User();
        user.setId(8L);
        user.setCreated(new Date());
        user.setPassword("sdfgsdf");
        user.setPhone("liuyan");
//        打印类信息
        System.out.println(user);
        System.out.println();

        // 序列化 对象转json字符串
        String json = MAPPER.writeValueAsString(user);
        System.out.println("序列化json = " + json);
        System.out.println();

//        反序列化 json转普通对象 两个参数：json字符串和反序列化的目标类字节码
        User user1 = MAPPER.readValue(json, User.class);
        System.out.println("反序列化：" + user1);
        System.out.println();

//        json转集合
        user1.setUsername("gg");
        User[] us = {user, user1};
        String jsons = MAPPER.writeValueAsString(us);
//        两个参数：json字符串和反序列化的目标类字节码
        List<User> users = MAPPER.readValue(jsons, MAPPER.getTypeFactory().constructCollectionType(List.class, User.class));
        for (User u : users) {
            System.out.println(u);
        }
        System.out.println();

//        json转换任意复杂类型
        String jsonss = MAPPER.writeValueAsString(Arrays.asList(user, user));
        List<User> users1 = MAPPER.readValue(jsonss, new TypeReference<List<User>>() {
        });
        System.out.println(users1);
        for (User u : users1) {
            System.out.println(u);
        }
        System.out.println();


    }
}
