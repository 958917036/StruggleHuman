package com.gc.study.java8.optional;

import java.util.Optional;

import org.junit.Test;

import com.gc.study.java8.lambda.User;
/*
 * Optional容器类的常用方法
 * 		Optional.of(T t): 创建一个Optional实例
 * 		Optional.empty(): 创建一个空的Optional实例
 * `	Optional.ofNullable(T t): 
 */
public class TestOptional {

	@Test
	public void testNullable() {
		Optional<User> op = Optional.ofNullable(new User());
		System.out.println(op.get());
		
		op = Optional.ofNullable(null);
		if(op.isPresent()) {
			System.out.println("nullable null:"+op.get());
		} 
		
	}
	@Test
	public void testMap() {
		Optional<User> op = Optional.ofNullable(new User("map test"));
		Optional<String> name = op.map(u -> u.getName());
		if(name.isPresent()) {
			System.out.println(name.get());
		} else {
			System.out.println("test map no element");
		}
	}
	/**
	 * 如果调用对象包含值，则返回该值，否则返回传入值
	 */
	@Test
	public void testOrelse() {
		Optional<User> op = Optional.empty();
		//如果调用对象包含值，则返回该值，否则返回传入值
		User user = op.orElse(new User("orelse"));
		System.out.println("orElse:"+user);
	}
	@Test
	public void testOrelseget() {
		Optional<User> op = Optional.empty();
		User user = op.orElseGet(() -> new User("orelseget"));
		System.out.println("orElseget:"+user);
	}
	@Test
	public void testEmpty() {
		Optional<User> op = Optional.empty();
		if(op.isPresent()) {
			System.out.println(op.get());
		} else {
			System.out.println("Empty");
		}
	}
	@Test
	public void testOf() {
		Optional<User> op = Optional.of(new User());
		User user = op.get();
		System.out.println(user);
	}

}
