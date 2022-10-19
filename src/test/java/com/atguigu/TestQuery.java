package com.atguigu;

import com.alibaba.fastjson.JSON;
import com.atguigu.entity.Goods;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
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
		searchSourceBuilder.size(10);//默认值10
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
		goodsArrayList.forEach(System.out::println);//940
		System.out.println(goodsArrayList.size());//Java中goodList集合
	}

	@Test
	public void testMatch() throws IOException {
		//创建查询的请求
		SearchRequest request = new SearchRequest("goods");
		//分页
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("title", "华为手机").operator(Operator.AND);
		searchSourceBuilder.query(queryBuilder);
		request.source(searchSourceBuilder);

		searchSourceBuilder.from(0);
		searchSourceBuilder.size(20);//默认值10
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
		goodsArrayList.forEach(System.out::println);//51
		System.out.println(goodsArrayList.size());//Java中goodList集合
	}

	@Test
	public void testTerm() throws IOException {
		//创建查询的请求
		SearchRequest request = new SearchRequest("goods");
		//分页
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		TermQueryBuilder queryBuilder = QueryBuilders.termQuery("categoryName", "手机");
		searchSourceBuilder.query(queryBuilder);
		request.source(searchSourceBuilder);

		searchSourceBuilder.from(0);
		searchSourceBuilder.size(20);//默认值10
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
		goodsArrayList.forEach(System.out::println);//728
		System.out.println(goodsArrayList.size());//Java中goodList集合
	}

	@Test
	public void testWildcard() throws IOException {
		//创建查询的请求
		SearchRequest request = new SearchRequest("goods");
		//分页
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		WildcardQueryBuilder queryBuilder = QueryBuilders.wildcardQuery("title", "华*");
		searchSourceBuilder.query(queryBuilder);
		request.source(searchSourceBuilder);

		searchSourceBuilder.from(0);
		searchSourceBuilder.size(20);//默认值10
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
		goodsArrayList.forEach(System.out::println);//57
		System.out.println(goodsArrayList.size());//Java中goodList集合
	}

	@Test
	public void testRange() throws IOException {
		//创建查询的请求
		SearchRequest request = new SearchRequest("goods");
		//分页
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		//条件
		RangeQueryBuilder queryBuilder = QueryBuilders.rangeQuery("price").gte(2000).lte(3000);
		searchSourceBuilder.query(queryBuilder);
		request.source(searchSourceBuilder);

		searchSourceBuilder.from(0);
		searchSourceBuilder.size(20);//默认值10
		searchSourceBuilder.sort("price", SortOrder.DESC);//排序
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
		goodsArrayList.forEach(System.out::println);//172
		System.out.println(goodsArrayList.size());//Java中goodList集合
	}

	@Test
	public void test_queryString() throws IOException {
		//创建查询的请求
		SearchRequest request = new SearchRequest("goods");
		//分页
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		//条件
		QueryStringQueryBuilder queryBuilder = QueryBuilders.queryStringQuery("华为手机").
				field("title").
				field("categoryName").//keyword
						field("brandName");//keyword
		searchSourceBuilder.query(queryBuilder);
		request.source(searchSourceBuilder);

		//searchSourceBuilder.from(0);
		//searchSourceBuilder.size(20);//默认值10
		//searchSourceBuilder.sort("price", SortOrder.DESC);//排序

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
		goodsArrayList.forEach(System.out::println);//718
		System.out.println(goodsArrayList.size());//Java中goodList集合
	}
}