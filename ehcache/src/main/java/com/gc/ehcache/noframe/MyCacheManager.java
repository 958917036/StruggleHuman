package com.gc.ehcache.noframe;

import java.io.File;
import java.time.Duration;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.impl.config.store.disk.OffHeapDiskStoreConfiguration;

/**
 * @author gc
 * 参考资料：
 *   https://www.ehcache.org/documentation/3.6/getting-started.html
 *   https://www.ehcache.org/documentation/3.6/tiering.html
 */
public class MyCacheManager {
	private CacheManager cacheManager;
	private String cache1 = "mycache1";
	private String cache2 = "mycache2";

	public void init() {
		// CacheManager cacheManager =
		// CacheManagerBuilder.newCacheManagerBuilder().withCache(cache1,
		// configurationBuilder).build();
		// 创建简单的缓存管理器
		cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();
		// 创建文件管理器时指定持久化存储的文件位置，具体是否使用磁盘缓存看具体的cache的配置
		cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
				.with(CacheManagerBuilder.persistence(new File("myCacheData"))).build();
		// 初始化缓存管理器
		cacheManager.init();
	}

	public void close() {
		//jvm关闭之前如果正常调用close方法，那么持久化到磁盘的缓存文件再重启时可以恢复。否则重启会被删除
		cacheManager.close();
	}
	public void destroyCache() {
		
	}

	private ResourcePoolsBuilder getResourcePoolBuilder() {
		//定义堆内资源池大小为10条数据，堆外(直接内存)资源池大小为1M.磁盘资源池为2M
		//直接内存的好处是减轻垃圾回收的压力，但是读写需要序列化和反序列化（disk也是）。disk一般用于缓存数据过大时使用
		//缓存存储策略可以设置成多层，多层的情况下必须包含heap层。层的大小应该是金字塔形的（按内存算），最快的层位于顶部
		//如果缓存存储位置配置了多层，那么插入的数据会直接放到最底层(然后校验上层缓存是否存在失效的)，在当前配置中会直接放到disk层（巨慢）。
		//读缓存的时候会先从上层读，找不到的话依次向下。然后更新上层缓存数据。如在disk层命中缓存，那么需要更新heap和offheap的缓存
		ResourcePoolsBuilder diskBuilder = ResourcePoolsBuilder.newResourcePoolsBuilder().
				heap(30000, EntryUnit.ENTRIES)
				.offheap(10, MemoryUnit.MB).disk(20, MemoryUnit.MB, true);
		return diskBuilder;
	}

	public <K, V> Cache<K, V> createPersistCache(String cacheName, Class<K> keyClass, Class<V> valueClass) {
		CacheConfigurationBuilder<K, V> configurationBuilder = CacheConfigurationBuilder
				.newCacheConfigurationBuilder(keyClass, valueClass, getResourcePoolBuilder());
		//磁盘存储提供多个段，这些段提供并发访问，也保存打开文件的文件指针。默认是16.下面配置成2
		configurationBuilder.add(new OffHeapDiskStoreConfiguration(2));
		//配置缓存过期策略，这里配置的是一条缓存项存活时间为10s
		configurationBuilder.withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(10)));
		return cacheManager.createCache(cacheName, configurationBuilder);
	}

	/**
	 * 创建缓存，注意这里是泛型方法，所以前面需要指定<K,V>
	 * 
	 * @param cacheName
	 *            缓存名(命名空间),每个命名空间对应维护自己的一份缓存
	 * @param keyClass
	 *            缓存的key类型
	 * @param valueClass
	 *            缓存的值类型
	 * @return 缓存类
	 */
	public <K, V> Cache<K, V> createCache(String cacheName, Class<K> keyClass, Class<V> valueClass) {
		CacheConfigurationBuilder<K, V> configurationBuilder = CacheConfigurationBuilder
				.newCacheConfigurationBuilder(keyClass, valueClass, ResourcePoolsBuilder.heap(10));
		return cacheManager.createCache(cacheName, configurationBuilder);
	}

	private <K, V> Cache<K, V> getCache(String cacheName, Class<K> keyClass, Class<V> valueClass) {
		Cache<K, V> cache = cacheManager.getCache(cacheName, keyClass, valueClass);
		if (cache != null) {
			return cache;
		} else {
			throw new RuntimeException("获取cache失败");
		}
	}

	public <K, V> V readCache(String cacheName, K key, Class<V> valueClass) {
		@SuppressWarnings("unchecked")
		Cache<K, V> cache = (Cache<K, V>) getCache(cacheName, key.getClass(), valueClass);
		V value = cache.get(key);
		return value;
	}

	public <K, V> void writeCache(String cacheName, K key, V value) {
		@SuppressWarnings("unchecked")
		Cache<K, V> cache = (Cache<K, V>) getCache(cacheName, key.getClass(), value.getClass());
		cache.put(key, value);
	}

	public <K, V> boolean updateCache(String cacheName, K key, V oldValue, V newValue) {
		@SuppressWarnings("unchecked")
		Cache<K, V> cache = (Cache<K, V>) getCache(cacheName, key.getClass(), oldValue.getClass());
		return cache.replace(key, oldValue, newValue);
	}

	public <K, V> void removeCache(String cacheName, K key, V value) {
		@SuppressWarnings("unchecked")
		Cache<K, V> cache = (Cache<K, V>) getCache(cacheName, key.getClass(), value.getClass());
		cache.remove(key, value);
	}

	public <K, V> void clearCache(String cacheName, K key, Class<V> valueClass) {
		@SuppressWarnings("unchecked")
		Cache<K, V> cache = (Cache<K, V>) getCache(cacheName, key.getClass(), valueClass);
		cache.clear();
	}

}
