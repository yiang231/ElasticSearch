package com.atguigu.test;

import com.atguigu.entity.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

@SpringBootTest
public class TestSpringDataES {
	@Autowired
	private ElasticsearchRestTemplate elasticsearchRestTemplate;

	@Test
	public void testTemplate() {
		System.out.println("elasticsearchRestTemplate = " + elasticsearchRestTemplate);
	}

	@Test
	public void testCreateIndex() {
		elasticsearchRestTemplate.createIndex(Item.class);//创建索引
		elasticsearchRestTemplate.putMapping(Item.class);//添加映射信息
	}

	@Test
	public void testAdd() {//添加一条文档
		Item item = new Item(1L, "小米手机7", " 手机", "小米", 3499.00, "http://image.leyou.com/13123.jpg");
		this.elasticsearchRestTemplate.save(item); //save update saveAll
		//this.elasticsearchRestTemplate.delete()
		//this.elasticsearchRestTemplate.get();
	}
}
