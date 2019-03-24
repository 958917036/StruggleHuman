package com.gc.study.java8.stream;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
//编题测试使用
public class TestStream2 {

	/*
	 * 1.给定一个数字列表，如何返回一个由每个数的平方构成的列表
	 * 给定【1，2，3，4，5】，返回【1，4，9，16，25】
	 */
	@Test
	public void test() {
		Integer[] nums = new Integer[]{1,2,3,4,5};
		Arrays.stream(nums).map((x) -> x * x).forEach(System.out::println);
	}

}
