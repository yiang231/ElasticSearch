package com.atguigu;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
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

	@Test
	public void testExists() throws IOException {
		//使用高级客户端创建一个操作索引的client
		IndicesClient indicesClient = client.indices();
		//创建一个（判断索引是否存在）请求
		GetIndexRequest request = new GetIndexRequest("person");
		//发出请求给ES，得到响应
		boolean exists = indicesClient.exists(request, RequestOptions.DEFAULT);
		//输出响应信息
		System.out.println(exists);
	}

	@Test
	public void testGet() throws IOException {
		//使用高级客户端创建一个操作索引的client
		IndicesClient indicesClient = client.indices();
		//创建一个（查询索引）请求
		GetIndexRequest request = new GetIndexRequest("person");
		//发出请求给ES
		GetIndexResponse response = indicesClient.get(request, RequestOptions.DEFAULT);
		//获取结果
		for (String key : response.getMappings().keySet()) {
			System.out.println(key + ":" + response.getMappings().get(key).getSourceAsMap());
		}
		System.out.println(response.getAliases());
		System.out.println(response.getSettings());
	}

	@Test
	public void testDelete() throws IOException {
		//使用高级客户端创建一个操作索引的client
		IndicesClient indicesClient = client.indices();
		//创建一个（删除索引）请求
		DeleteIndexRequest request = new DeleteIndexRequest("person");
		//发出请求给ES
		AcknowledgedResponse response = indicesClient.delete(request, RequestOptions.DEFAULT);
		//输出响应信息
		System.out.println(response.isAcknowledged());
	}
}
