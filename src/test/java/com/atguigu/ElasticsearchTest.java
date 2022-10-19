package com.atguigu;

import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ElasticsearchTest {
	@Autowired
	//@Qualifier("restHighLevelClient")
	private RestHighLevelClient client;

	@Test
	public void contextLoads() {
		System.out.println(client);
	}
}
