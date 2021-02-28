import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/3/1 3:07 上午
 */
public class Test4 {
    public static void main(String[] args) {

        MongoClient client = new MongoClient("10.108.163.120", 27017);

//        MongoClientURI connectionString = new MongoClientURI("mongodb://root:123456789@10.108.163.120:27017/spitdb");
//        MongoClient client = new MongoClient(connectionString);
//        MongoClient client = new MongoClient("localhost", 27017);
        MongoDatabase database = client.getDatabase("spitdb");
        MongoCollection<Document> spit = database.getCollection("spit");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("content", "测试");
        map.put("userid", "999");
        map.put("visits", "9999");
        map.put("publishtime", new Date());
        Document documentt = new Document(map);

//        插入
        spit.insertOne(documentt);

        FindIterable<Document> documents = spit.find();

        for (Document document : documents) {
            System.out.println("内容：" + document.getString("content"));
            System.out.println("用户ID: " + document.getString("userid"));
            System.out.println("浏览量： " + document.getString("visits"));
            System.out.println("-----------------------------------------------");
        }
        client.close();
    }
}
