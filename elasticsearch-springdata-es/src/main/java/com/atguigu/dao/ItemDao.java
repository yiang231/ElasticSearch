package com.atguigu.dao;

import com.atguigu.entity.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ItemDao extends ElasticsearchRepository<Item, Long> {
}
