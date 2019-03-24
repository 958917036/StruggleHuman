package com.gc.study.java8.end;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.omg.Messaging.SyncScopeHelper;

import com.gc.study.java8.lambda.User;
import com.gc.study.java8.lambda.User.Status;

/**
 * 测试lambda的学习效果
 * @author gc
 *
 */
public class TestStudyResult {
	List<User> users = Arrays.asList(
			new User("gc1", 24, 7500, Status.BUSY),
			new User("gc2", 25, 13000, Status.FREE),
			new User("gc3", 26, 20000, Status.VOCATION),
			new User("gc4", 23, 2000, Status.BUSY),
			new User("gc5", 22, 0, Status.FREE),
			new User("gc6", 24, 7500, Status.BUSY),
			new User("gc7", 25, 13000, Status.FREE),
			new User("gc8", 26, 20000, Status.VOCATION),
			new User("gc9", 23, 2000, Status.BUSY),
			new User("gc10", 22, 0, Status.FREE));
	/**
	 * 求年龄的和，年龄的最大值，最小值, 平均值
	 */
	@Test
	public void test1() {
		Integer sum1 = users.stream().map(x -> x.getAge()).reduce(0,(x,y) -> x +y);
		Integer sum2 = users.stream().map(x -> x.getAge()).collect(Collectors.summingInt((x) -> x));
		Integer min = users.stream().map(x -> x.getAge()).collect(Collectors.minBy((x,y) -> x - y)).get();
		Integer max1 = users.stream().max((x,y) -> x.getAge() - y.getAge()).get().getAge();
		Integer max2 = users.stream().map(x -> x.getAge()).max(Integer::compare).get();
		Integer max3 = users.stream().map(x -> x.getAge()).collect(Collectors.maxBy((x,y) -> x -y)).get();
		Double avg = users.stream().collect(Collectors.averagingInt(User::getAge));
		System.out.println("sum:"+sum1+"/"+sum2+", min age:"+min+", max age:"+max1+"/"+max2+"/"+max3+", avg:"+avg);
	}
	
	/**
	 * 按年龄排序，按姓名排序，结果转为对应的数组
	 */
	/**
	 * 
	 */
	@Test
	public void testSort() {
		users.stream().sorted((x,y) -> x.getAge() - y.getAge()).forEach(x -> System.out.print(x.getAge()+"、"));
		System.out.println();
		Integer[] ages = users.stream().sorted((x,y) -> x.getAge() - y.getAge()).map(x -> x.getAge()).toArray(Integer[]::new);
		System.out.println();
		Stream.of(ages).forEach(x -> System.out.print(x+","));
		
		System.out.println();
		String[] names = users.stream().map(x -> x.getName()).sorted().toArray(String[]::new);
		Stream.of(names).forEach(x -> System.out.print(x+","));
		System.out.println();

		ArrayList[] list = Stream.of(names).toArray(ArrayList[]::new);
		System.out.println();
	}
	
	/**
	 * list转为set， list转为以name为key的数组，
	 */
	@Test
	public void testCollect() {
		Set<User> set1 = users.stream().collect(Collectors.toSet());
		set1.stream().forEach(System.out::println);
		
		Map<String, User> userMap1 = users.stream().collect(Collectors.toMap(User::getName , x -> x));
		Stream.of(userMap1).forEach(System.out::println);
		
		//tomap key不能重复，否则报错，这里toMap 最后一个参数(x,y) -> y) 表示如果key冲突了则直接合并
		Map<Integer, User> ageMap = users.stream().collect(Collectors.toMap(User::getAge, x -> x, (x,y) -> y));
		Stream.of(ageMap).forEach(x -> System.out.println(x.keySet()));
		ageMap.entrySet().forEach(x -> System.out.println(x.getKey()));
		
		Map<Status, List<User>> group1 = users.stream().collect(Collectors.groupingBy(User::getStatus));
		group1.entrySet().stream().forEach(System.out::println);
		
	}
}
