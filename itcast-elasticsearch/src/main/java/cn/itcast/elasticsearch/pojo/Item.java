package cn.itcast.elasticsearch.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/9 4:24 下午
 */

@Data
//使用注解对Elasticsearch相关属性进行配置
@Document(indexName = "item", type = "docs", shards = 1, replicas = 0)//注解名 注解存储类型 分片数 备份数
public class Item {
    @Id//标记一个字段作为id主键
    Long id;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    String title; //标题
    @Field(type = FieldType.Keyword)
    String category;// 分类
    @Field(type = FieldType.Keyword)
    String brand; // 品牌
    @Field(type = FieldType.Double)
    Double price; // 价格
    @Field(index = false, type = FieldType.Keyword)
    String images; // 图片地址

    //@Data注解中没有全部的构造方法
    public Item() {
    }

    public Item(Long id, String title, String category, String brand, Double price, String images) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.brand = brand;
        this.price = price;
        this.images = images;
    }
}