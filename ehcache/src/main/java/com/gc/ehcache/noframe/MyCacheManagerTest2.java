package com.gc.ehcache.noframe;

import java.util.Arrays;
import java.util.List;

import org.ehcache.Cache;
import org.ehcache.config.ResourcePools;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 测试带持久化的缓存，以及超出大小的清理策略
 * @author gc
 */
public class MyCacheManagerTest2 {

	MyCacheManager cacheManager;
	String cacheName = "test-cache2";
	Cache<Integer, String> cache;
	int size;
	@Before
	public void before() {
		cacheManager = new MyCacheManager();
		cacheManager.init();
		cache = cacheManager.createPersistCache(cacheName, Integer.class, String.class);
	}
	@After
	public void after() {
		cacheManager.close();
	}
	@Test
	public void testHeapAdd() {
		System.out.println("测试所有数据都放到内存缓存中时的写入速度");
		String cacheName = "test-cache22";
		cache = cacheManager.createCache(cacheName, Integer.class, String.class);
		//动态的修改cache的配置，这里是将内存缓存条数放大到40000
		ResourcePools pools = ResourcePoolsBuilder.newResourcePoolsBuilder().heap(40000, EntryUnit.ENTRIES).build();
		cache.getRuntimeConfiguration().updateResourcePools(pools);
		
		List<Integer> list = Arrays.asList(1,100,1000,10000,20000,40000);
		for (Integer size : list) {
			cache.clear();
			addWithTime(size);
		}
		//printCache("添加entry");
	}
	@Test
	public void testPersistAdd() {
		System.out.println("测试使用多层缓存时的写入速度(当前的测试量可以理解为全部写到了磁盘上)");
		List<Integer> list = Arrays.asList(1,100,1000,10000,20000,40000);
		for (Integer size : list) {
			cache.clear();
			addWithTime(size);
		}
		//printCache("添加entry");
	}
	private void addWithTime(int size) {
		long startTime = System.currentTimeMillis();
		add(size);
		long endTime = System.currentTimeMillis();
		System.out.println("end add.add size:"+size+" ,last size:"+Tool.getCacheSize(cache)+" ,cost time:"+(endTime-startTime)+"ms");
	}
	
	private void add(int size) {
		//Tool.add(cache, size);
		for (int i = 1; i <= size; i++) {
			cache.put(i, "abcdefghij");
		}
	}
	private void printCache(String desc) {
		Tool.printCache(cache, desc);
	}
}
