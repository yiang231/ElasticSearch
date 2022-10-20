package com.atguigu;

import com.alibaba.fastjson.JSON;
import com.atguigu.entity.Goods;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
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
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Max;
import org.elasticsearch.search.aggregations.metrics.MaxAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

	/**
	 * QueryBuilder: 指定查询条件 matchQuery termQuery  rangeQuery  wildCardQuery boolQuery()
	 * SearchSourceBuilder:不仅可以包含查询条件，还可以指定排序、分页、高亮、聚合
	 */
	@Test
	public void testBoolean() throws IOException {
		//创建查询的请求
		SearchRequest request = new SearchRequest("goods");
		//分页
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		//条件
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

		TermQueryBuilder queryBuilder1 = QueryBuilders.termQuery("brandName", "华为");
		boolQueryBuilder.must(queryBuilder1);

		TermQueryBuilder queryBuilder2 = QueryBuilders.termQuery("title", "手机");
		boolQueryBuilder.filter(queryBuilder2);

		RangeQueryBuilder queryBuilder3 = QueryBuilders.rangeQuery("price").gte(2000).lte(3000);
		boolQueryBuilder.filter(queryBuilder3);

		//其他布尔查询条件
		//boolQueryBuilder.mustNot();
		//boolQueryBuilder.should();

		searchSourceBuilder.query(boolQueryBuilder);
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
		goodsArrayList.forEach(System.out::println);//16
		System.out.println(goodsArrayList.size());//Java中goodList集合
	}

	@Test
	public void testHighlight() throws IOException {
		//创建查询的请求
		SearchRequest request = new SearchRequest("goods");
		//分页
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		//条件
		MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("title", "电视");
		searchSourceBuilder.query(queryBuilder);
		request.source(searchSourceBuilder);

		HighlightBuilder highlightBuilder = new HighlightBuilder();
		highlightBuilder.field("title");
		highlightBuilder.preTags("<font color='red'>");
		highlightBuilder.postTags("</font>");
		searchSourceBuilder.highlighter(highlightBuilder);
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

			//高亮
			//拿到所有高亮属性HashMap
			Map<String, HighlightField> highlightFields = hit.getHighlightFields();
			//从属性中拿到要高亮的title属性
			HighlightField highlightField = highlightFields.get("title");
			//拿到title属性的片段
			Text[] fragments = highlightField.fragments();
			//片段中title是片段中有且只有一个の元素
			String title = fragments[0].toString();
			goods.setTitle(title);
			goodsArrayList.add(goods);
		}
		//遍历List<Goods>输出
		goodsArrayList.forEach(System.out::println);//69
		System.out.println(goodsArrayList.size());//Java中goodList集合
	}

	/*
	指标聚合：相当于MySQL的聚合函数。max、min、avg、sum等
	桶聚合：相当于MySQL的 group by count操作。不要对text类型的数据进行分组，会失败。
	*/
	@Test
	public void testAggBucketQuery() throws IOException {
		//创建查询的请求
		SearchRequest request = new SearchRequest("goods");
		//分页
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		//条件
		MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("title", "电视");
		searchSourceBuilder.query(queryBuilder);
		request.source(searchSourceBuilder);

		TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("goods_brands").field("brandName").size(100);
		searchSourceBuilder.aggregation(aggregationBuilder);
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

			//高亮
			//拿到所有高亮属性HashMap
			/*Map<String, HighlightField> highlightFields = hit.getHighlightFields();
			//从属性中拿到要高亮的title属性
			HighlightField highlightField = highlightFields.get("title");
			//拿到title属性的片段
			Text[] fragments = highlightField.fragments();
			//片段中title是片段中第一个
			String title = fragments[0].toString();
			goods.setTitle(title);*/

			goodsArrayList.add(goods);
		}
		//遍历List<Goods>输出
		goodsArrayList.forEach(System.out::println);//69
		System.out.println(goodsArrayList.size());//Java中goodList集合

		//获取桶聚合的信息
		Aggregations aggregations = response.getAggregations();
		Map<String, Aggregation> aggregationMap = aggregations.asMap();
		Terms bucketsName = (Terms) aggregationMap.get("goods_brands");
		List<? extends Terms.Bucket> buckets = bucketsName.getBuckets();
		buckets.forEach(bucket -> System.out.println("key : " + bucket.getKey() + "," + "doc_count : " + bucket.getDocCount()));
	}

	@Test
	public void testAggMaxQuery() throws IOException {
		//创建查询的请求
		SearchRequest request = new SearchRequest("goods");
		//分页
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		//条件
		MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("title", "华为手机");
		searchSourceBuilder.query(queryBuilder);
		request.source(searchSourceBuilder);

		MaxAggregationBuilder aggregationBuilder = AggregationBuilders.max("max_price").field("price");
		searchSourceBuilder.aggregation(aggregationBuilder);
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

			//高亮
			//拿到所有高亮属性HashMap
			/*Map<String, HighlightField> highlightFields = hit.getHighlightFields();
			//从属性中拿到要高亮的title属性
			HighlightField highlightField = highlightFields.get("title");
			//拿到title属性的片段
			Text[] fragments = highlightField.fragments();
			//片段中title是片段中第一个
			String title = fragments[0].toString();
			goods.setTitle(title);*/

			goodsArrayList.add(goods);
		}
		//遍历List<Goods>输出
		goodsArrayList.forEach(System.out::println);//69
		System.out.println(goodsArrayList.size());//Java中goodList集合

		//获取聚合的信息（最高的价格怎么获得？？？）
		Aggregations aggregations = response.getAggregations();
		Max maxPrice = aggregations.get("max_price");
		System.out.println(maxPrice.getValue());
	}
}