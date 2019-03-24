package com.gc.study.java8.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.Test;

/**
 * @author gc、
 * java8 内置的四大核心函数式接口
 * 
 * Consumer<T>: 消费型接口
 * 		void accept(T t);
 * Supplier<T>: 供给型接口
 * 		T get()
 * Function<T, R>: 函数型接口
 * 		R apply(T t);
 * Predicate<T>: 断言型接口
 * 		boolean test(T t);
 *
 */
public class TestLambda3 {

	@Test
	//Consumer<T> 消费型接口
	public void testConsumer() {
		happy(10000L, m -> System.out.println("大保健，消费："+m+" 元"));
	}
	public void happy(double money, Consumer<Double> con) {
		con.accept(money);
	}
	
	//Supplier<T> 供给型接口
	@Test
	public void testSupplier() {
		List<Integer> numList = getNumList(10, () -> (int)(Math.random() * 100));
		System.out.println(numList);
	}
	
	public List<Integer> getNumList(int num, Supplier<Integer> sup) {
		List<Integer> list = new ArrayList<>();
		
		for (int i = 0; i < num; i++) {
			Integer n = sup.get();
			list.add(n);
		}
		return list;
	}
	
	//Function<T,R> 函数型接口
	//需求：用于处理字符串
	@Test
	public void testFunction() {
		String str = strHandler("我最帅", (x) -> (x + ", 哈哈哈"));
		System.out.println(str);
	}
	private String strHandler(String str, Function<String, String> fun) {
		return fun.apply(str);
	}
	
	//Predicate<T> 断言型接口
	//需求：将满足条件的字符串放入集合中
	@Test
	public void testPredicate() {
		List<String> list = Arrays.asList("Hello", "World", "www.exact.com");
		List<String> filterStr = filterStr(list, x -> (x.length() > 5));
		System.out.println(filterStr);
	}
	
	public List<String> filterStr(List<String> list, Predicate<String> pre) {
		List<String> strList = new ArrayList<>();
		
		for (String str : list) {
			if(pre.test(str)) {
				strList.add(str);
			}
		}
		return strList;
	}
	

}
