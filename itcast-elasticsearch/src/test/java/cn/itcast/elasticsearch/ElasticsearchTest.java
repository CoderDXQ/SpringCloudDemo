package cn.itcast.elasticsearch;

import cn.itcast.elasticsearch.pojo.Item;
import com.sun.jna.Native;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/9 5:01 下午
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchTest {

    //    注入Elasticsearch模板
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testCreateIndex() {
//        创建索引和映射
        this.elasticsearchTemplate.createIndex(Item.class);
        this.elasticsearchTemplate.putMapping(Item.class);
    }

    @Test
    public void deleteIndex() {
        elasticsearchTemplate.deleteIndex("heima");
    }

    //测试新增文档
    @Test
    public void testCreateDocs() {

//        单个新增
        Item item = new Item(1L, "小米手机7", " 手机", "小米", 3499.00, "http://image.leyou.com/13123.jpg");

        this.itemRepository.save(item);

//        批量新增
        List<Item> list = new ArrayList<>();
        list.add(new Item(2L, "坚果手机R1", " 手机", "锤子", 3699.00, "http://image.leyou.com/123.jpg"));
        list.add(new Item(3L, "华为META10", " 手机", "华为", 4499.00, "http://image.leyou.com/3.jpg"));
        list.add(new Item(1L, "小米手机7", "手机", "小米", 3299.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(2L, "坚果手机R1", "手机", "锤子", 3699.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(3L, "华为META10", "手机", "华为", 4499.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(4L, "小米Mix2S", "手机", "小米", 4299.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(5L, "荣耀V10", "手机", "华为", 2799.00, "http://image.leyou.com/13123.jpg"));
        // 接收对象集合，实现批量新增
        this.itemRepository.saveAll(list);
    }

    @Test
    public void testfind() {

        Iterable<Item> items = this.itemRepository.findAll(Sort.by("price").descending());
        items.forEach(System.out::println);

    }

    @Test
    public void testfindbytitle() {
        Iterable<Item> items = this.itemRepository.findByTitle("手机");
        items.forEach(System.out::println);
    }

    @Test
    public void testfindbetween() {
        List<Item> list = this.itemRepository.findByPriceBetween(3000d, 4000d);
        list.forEach(System.out::println);

    }

    @Test
    public void testsearch() {

//        通过查询构建器工具构建查询条件   查询标题中带"手机"字样的记录
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("title", "手机");

//        执行查询，获得结果集
        Iterable<Item> items = this.itemRepository.search(queryBuilder);
        items.forEach(System.out::println);
    }

    //    自定义查询及高级查询
    @Test
    public void testNative() {

//        构建自定义查询器 帮助构建json的请求体
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

//        添加基本的查询条件
        queryBuilder.withQuery(QueryBuilders.matchQuery("category", "手机"));

//        添加分页条件，页码是从0开始
        queryBuilder.withPageable(PageRequest.of(1, 2));

//        添加排序条件，默认是降序
        queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));

//        执行查询获取分页结果集
        Page<Item> itemPage = this.itemRepository.search(queryBuilder.build());
        System.out.println(itemPage.getTotalPages());
        System.out.println(itemPage.getTotalElements());
//        itemPage.forEach(System.out::println);
        itemPage.getContent().forEach(System.out::println);

    }

    @Test
    public void testAggs() {
//        初始化自定义查询构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
//        添加聚合
        queryBuilder.addAggregation(AggregationBuilders.terms("brandAgg").field("brand"));
//        添加结果集过滤，不包括任何字段
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{}, null));
//        执行聚合查询
        AggregatedPage<Item> itemPage = (AggregatedPage<Item>) this.itemRepository.search(queryBuilder.build());
//        解析聚合结果集，根据聚合的类型以及字段类型要进行强转，brand是字符串类型的，聚合类型-词条聚合，brandAgg-通过聚合名称获取聚合对
        StringTerms brandAgg = (StringTerms) itemPage.getAggregation("brandAgg");
//        获取桶的集合
        List<StringTerms.Bucket> buckets = brandAgg.getBuckets();
        buckets.forEach(bucket -> {
//            获取字符串类型的聚合
            System.out.println(bucket.getKeyAsString());
//            获取每个聚合里的记录数
            System.out.println(bucket.getDocCount());
        });

    }

    @Test
    public void testsubAggs() {
//        初始化自定义查询构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
//        添加聚合 聚合名称分类条件以及求平均的运算
        queryBuilder.addAggregation(AggregationBuilders.terms("brandAgg").field("brand").subAggregation(AggregationBuilders.avg("price_avg").field("price")));
//        添加结果集过滤，不包括任何字段
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{}, null));
//        执行聚合查询
        AggregatedPage<Item> itemPage = (AggregatedPage<Item>) this.itemRepository.search(queryBuilder.build());


        System.out.println("输出总的查询结果");
        System.out.println(itemPage.getContent().toString());
        System.out.println();

//        聚合是对查询的结果进行处理
//        解析聚合结果集，根据聚合的类型以及字段类型要进行强转，brand是字符串类型的，聚合类型-词条聚合，brandAgg-通过聚合名称获取聚合对
        StringTerms brandAgg = (StringTerms) itemPage.getAggregation("brandAgg");
//        获取桶的集合
        List<StringTerms.Bucket> buckets = brandAgg.getBuckets();
        buckets.forEach(bucket -> {
//            获取字符串类型的聚合
            System.out.println("Key: " + bucket.getKeyAsString());
//            获取每个聚合里的记录数
            System.out.println("Count: " + bucket.getDocCount());

//            获取子聚合的map集合：key-聚合名称，value-对应的子集合对象
            Map<String, Aggregation> stringAggregationMap = bucket.getAggregations().asMap();
            InternalAvg price_avg = (InternalAvg) stringAggregationMap.get("price_avg");
            System.out.println(price_avg.getValue());
            System.out.println();
        });

    }


}
