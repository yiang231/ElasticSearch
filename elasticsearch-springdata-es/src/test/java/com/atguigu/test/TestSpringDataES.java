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
}
