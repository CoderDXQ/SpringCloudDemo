package cn.itcast.elasticsearch;

import cn.itcast.elasticsearch.pojo.Item;
import com.sun.jna.Native;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

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

    //    自定义查询
    @Test
    public void testNative() {

//        构建自定义查询器
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


}
