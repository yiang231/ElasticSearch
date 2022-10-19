package com.atguigu;

import com.alibaba.fastjson.JSON;
import com.atguigu.entity.Goods;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TestQuery {
	@Autowired
	private RestHighLevelClient client;

	@Test
	public void testMatchAll() throws IOException {
		//创建查询的请求
		SearchRequest request = new SearchRequest("goods");
		//分页
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		MatchAllQueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
		searchSourceBuilder.query(queryBuilder);
		request.source(searchSourceBuilder);

		searchSourceBuilder.from(0);
		searchSourceBuilder.size(10);//默认值
		//执行查询请求并且得到响应
		SearchResponse response = client.search(request, RequestOptions.DEFAULT);
		//将结果封装到List<Goods>中
		List<Goods> goodsArrayList = new ArrayList<>();
		SearchHits hits = response.getHits();
		long totalHits = hits.getTotalHits().value;
		System.out.println("totalHits = " + totalHits);//ES中文档数量，goods索引的总数量

		SearchHit[] hitsArr = hits.getHits();
		for (SearchHit hit : hitsArr) {
			//获取文档字符串
			String hitSourceAsString = hit.getSourceAsString();
			//将文档字符串转换为goods对象
			Goods goods = JSON.parseObject(hitSourceAsString, Goods.class);
			goodsArrayList.add(goods);
		}
		//遍历List<Goods>输出
		goodsArrayList.forEach(System.out::println);
		System.out.println(goodsArrayList.size());//Java中goodList集合
	}
}
