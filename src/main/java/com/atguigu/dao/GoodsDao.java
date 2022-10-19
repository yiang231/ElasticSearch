package com.atguigu.dao;

import com.atguigu.entity.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsDao {
	public List<Goods> findAll();
}
