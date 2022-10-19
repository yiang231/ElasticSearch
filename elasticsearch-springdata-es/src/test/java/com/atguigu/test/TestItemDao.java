package com.atguigu.test;

import com.atguigu.dao.ItemDao;
import com.atguigu.entity.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class TestItemDao {
	@Autowired
	private ItemDao itemDao;

	@Test
	public void testSave() {
		Item item = new Item(2L, "小米手机2", " 手机2", "小米2", 2499.00, "http://image.leyou.com/13123.jpg");
		itemDao.save(item);
	}

	@Test
	public void testUpdate() {//根据id是否存在来添加或者修改
		Item item = new Item(2L, "小米手机22", " 手机22", "小米2", 2499.00, "http://image.leyou.com/13123.jpg");
		itemDao.save(item);
	}

	@Test
	public void testSaveAll() {
		List<Item> list = new ArrayList<>();
		list.add(new Item(3L, "坚果手机R1", " 手机", "锤子", 3699.00, "http://image.leyou.com/123.jpg"));
		list.add(new Item(4L, "华为mate10", " 手机", "华为", 4499.00, "http://image.leyou.com/3.jpg"));
		this.itemDao.saveAll(list);
	}

	@Test
	public void testGetById() {
		Optional<Item> optional = this.itemDao.findById(1L);
		System.out.println(optional.get());
	}

	@Test
	public void testFindAll() {
		//Iterable 不是iterator
		Iterable<Item> itemList = this.itemDao.findAll(Sort.by(Sort.Order.desc("price")));
		itemList.forEach(System.out::println);
	}

	@Test
	public void testDeleteById() {
		this.itemDao.deleteById(1L);
	}

	@Test
	public void testFindByPriceBetween() {
		List<Item> list = itemDao.findByPriceBetween(2500.0, 5000.0);
		list.forEach(System.out::println);
	}

	@Test
	public void testFindByPriceAndTitle() {
		List<Item> list = itemDao.findByPriceAndTitle(3699.0, "小米手机");
		list.forEach(System.out::println);
	}

	@Test
	public void testFindByPriceOrTitle() {
		List<Item> list = itemDao.findByPriceOrTitle(3699.0, "手机");
		list.forEach(System.out::println);
	}
}
