package com.atguigu.dao;

import com.atguigu.entity.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ItemDao extends ElasticsearchRepository<Item, Long> {
	List<Item> findByPriceBetween(Double price1, Double price2);

	//org.springframework.data.mapping.PropertyReferenceException: No property name found for type Item!
	//方法名中的Title不能为Name，不能随意起名
	List<Item> findByPriceAndTitle(Double price, String name);//两个条件同时满足

	List<Item> findByPriceOrTitle(Double price, String name);
}
