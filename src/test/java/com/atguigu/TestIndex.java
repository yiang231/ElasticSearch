package com.atguigu;

import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class TestIndex {
	@Autowired
	private RestHighLevelClient client;

	@Test
	public void testAddWithoutMapping() throws IOException {
		//使用高级客户端创建一个操作索引的client
		IndicesClient indicesClient = client.indices();
		//创建一个（创建索引）请求
		//put操作
		CreateIndexRequest request = new CreateIndexRequest("person");
		//发出请求给ES
		CreateIndexResponse response = indicesClient.create(request, RequestOptions.DEFAULT);
		//输出响应信息
		System.out.println(response.index());
		System.out.println(response.isAcknowledged());
		System.out.println(response.isShardsAcknowledged());
	}

	@Test
	public void testAddWithMapping() throws IOException {
		//使用高级客户端创建一个操作索引的client
		IndicesClient indicesClient = client.indices();
		//创建一个（创建索引）请求
		//put操作
		CreateIndexRequest request = new CreateIndexRequest("person");
		String source = "{\n" +
				"     \"properties\":{\n" +
				"        \"address\":{\n" +
				"          \"type\":\"text\",\n" +
				"          \"analyzer\":\"ik_max_word\"\n" +
				"        },\n" +
				"        \"age\":{\n" +
				"          \"type\":\"long\"\n" +
				"        },\n" +
				"        \"name\":{\n" +
				"          \"type\":\"keyword\"\n" +
				"        }\n" +
				"      }\n" +
				"  }";
		request.mapping(source, XContentType.JSON);
		//发出请求给ES
		CreateIndexResponse response = indicesClient.create(request, RequestOptions.DEFAULT);
		//输出响应信息
		System.out.println(response.index());
		System.out.println(response.isAcknowledged());
		System.out.println(response.isShardsAcknowledged());
	}
}
