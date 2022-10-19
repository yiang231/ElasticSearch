package com.atguigu;

import com.alibaba.fastjson.JSON;
import com.atguigu.dao.GoodsDao;
import com.atguigu.entity.Goods;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class TestImport {
	@Autowired
	private RestHighLevelClient client;
	@Autowired
	private GoodsDao goodsMapper;

	@Test
	public void test01() throws IOException {
		//查询出所有的数据
		List<Goods> goodsList = goodsMapper.findAll();
		//批量导入ES
		BulkRequest bulkRequest = new BulkRequest();
		for (Goods goods : goodsList) {
			//接收数据库信息变为JSON字符串
			String specStr = goods.getSpecStr();
			//将JSON字符串变为Map集合
			Map goodsMap = JSON.parseObject(specStr, Map.class);
			goods.setSpec(goodsMap);

			//将goods对象转为JSON字符串
			String goodsJSON = JSON.toJSONString(goods);
			//创建索引
			IndexRequest request = new IndexRequest("goods");
			request.id(goods.getId() + "").source(goodsJSON, XContentType.JSON);
			bulkRequest.add(request);
		}
		BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
		System.out.println(response.status());
	}
}
