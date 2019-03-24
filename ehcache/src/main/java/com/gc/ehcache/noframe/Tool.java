package com.gc.ehcache.noframe;

import java.util.ArrayList;
import java.util.List;

import org.ehcache.Cache;

public class Tool {
	public static void add(Cache<Integer, String> cache, int size) {
		for (int i = 1; i <= size; i++) {
			cache.put(i, "test"+i);
		}
	}
	
	public static int getCacheSize(Cache<Integer, String> cache) {
		List<Integer> list = new ArrayList();
		cache.forEach(x -> {
			list.add(x.getKey());
		});
		return list.size();
	}
	/**
	 * 按照key的自然顺序，遍历打印缓存中数据
	 */
	public static void printCache(Cache<Integer, String> cache, String desc) {
		//cache.forEach(x -> System.out.print("["+x.getKey()+":"+x.getValue()+"],"));
		List<Integer> list = new ArrayList();
		cache.forEach(x -> {
			list.add(x.getKey());
		});
		System.out.println(desc+" ,cache size:"+list.size());
		System.out.print("  --");
		if(list.isEmpty()) {
			System.out.println("cache is empty");
		} else {
			list.stream().sorted().forEach(x -> {
				String v = cache.get(x);
				System.out.print("["+x+":"+v+"],");
			});
			System.out.println();
		}
	}
}
