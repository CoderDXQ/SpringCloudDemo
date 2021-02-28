import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/3/1 3:06 上午
 */
public class Test3 {
    public static void main(String[] args) {

        MongoClient client = new MongoClient("10.108.163.120", 27017);

//        MongoClientURI connectionString = new MongoClientURI("mongodb://root:123456789@10.108.163.120:27017/spitdb");
//        MongoClient client = new MongoClient(connectionString);
//        MongoClient client = new MongoClient("localhost", 27017);
        MongoDatabase database = client.getDatabase("spitdb");
        MongoCollection<Document> spit = database.getCollection("spit");
        BasicDBObject basicDBObject = new BasicDBObject("visits", new BasicDBObject("$gt", 1000));
        FindIterable<Document> documents = spit.find(basicDBObject);

        for (Document document : documents) {
            System.out.println("内容：" + document.getString("content"));
            System.out.println("用户ID: " + document.getString("userid"));
            System.out.println("浏览量： " + document.getString("visits"));
            System.out.println("-----------------------------------------------");
        }
        client.close();
    }
}
