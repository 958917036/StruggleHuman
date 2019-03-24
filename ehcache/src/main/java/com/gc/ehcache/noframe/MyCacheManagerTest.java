package com.gc.ehcache.noframe;

import java.util.ArrayList;
import java.util.List;

import org.ehcache.Cache;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 测试常规的增删改查动作
 * @author gc
 */
public class MyCacheManagerTest {

	MyCacheManager cacheManager;
	String cacheName = "test-cache";
	Cache<Integer, String> cache;
	@Before
	public void before() {
		cacheManager = new MyCacheManager();
		cacheManager.init();
		cache = cacheManager.createCache(cacheName, Integer.class, String.class);
	}
	@After
	public void after() {
		cacheManager.close();
	}
	@Test
	public void testAdd() {
		add();
		System.out.println(cache.get(1));
	}
	
	@Test
	public void testUpdate() {
		add();
		System.out.println("before update:"+cache.get(1));
		cache.replace(1, "test1", "i am new");
		System.out.println("after update:"+cache.get(1));
	}
	@Test
	public void testRemoveAndClear() {
		add();
		printCache("before remove");
		cache.remove(1);
		printCache("after remove");
		cache.clear();
		printCache("after clear");
	}
	
	private void add() {
		Tool.add(cache, 10);
	}
	private void add(int size) {
		Tool.add(cache, size);
	}
	private void printCache(String desc) {
		Tool.printCache(cache, desc);
	}
}
