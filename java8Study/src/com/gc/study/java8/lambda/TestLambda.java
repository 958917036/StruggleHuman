package com.gc.study.java8.lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Test;

/**
 * @author gc
 * Lambda  表达式的基础语法：java8中引入一个新的操作符 "->" ,该操作符称为箭头操作符或lambda操作符
 *  	箭头操作符将lambda拆分成两部分：
 *  	左侧：lambda表达式的参数列表
 *  	右侧：lambda表达式中所需执行的功能，即lambda体
 *  语法格式一：无参数，无返回值
 *  	() -> System.out.println("xxx");
 *  语法格式二：有一个参数，无返回值
 *  	(x) -> System.out.println(x);
 *  语法格式三：若只有一个参数，小括号可以省略不写
 *  	x -> System.out.println(x);
 *  语法格式四：有两个以上的参数，有返回值，并且lambda体中有多条语句    test4
 *  	Comparator<Integer> comparator = (x,y) -> {
 *			System.out.println("函数式接口");
 *			return Integer.compare(x, y);
 *		};
 *	语法格式五：若lambda体中只有一条语句，则return和大括号都可以省略不写
 *
 *	语法格式六：lambda表达式的参数列表的数据类型可以省略不写，因为jvm编译器可以根据上下文推断出数据类型，即“类型推断”
 *		(Integer x,Integer y) -> Integer.compare(x, y);  == (x,y) -> Integer.compare(x, y);
 *	
 *	左右遇一括号省， 左侧推断类型省，能省则省	
 *
 *	二、lambda表达式需要函数式接口的支持
 *		函数式接口：接口中只有一个抽象方法的接口，可以使用 @FunctionalInterface检查一下
 *		反而言之：jdk接口上有@FunctionalInterface注解的都是函数式接口
 */
public class TestLambda {
	
	@Test
	public void test1() {
		//相当于实现接口Runable无参方法run的匿名实现类
		Runnable r1 = () -> System.out.println("hello lambda");
		r1.run();
	}
	@Test
	public void test2() {
		Consumer<String> consumer = x -> System.out.println(x);
		consumer.accept("我很帅");
	}
	
	@Test
	public void test4() {
		Comparator<Integer> comparator = (x,y) -> {
			System.out.println("函数式接口");
			return Integer.compare(x, y);
		};
		Comparator<Integer> comparator2 = (x,y) -> Integer.compare(x, y);
	}
	
	//
	@Test
	public void test5() {
		Integer result = operation(100, (x) -> (x + 1));
		System.out.println(result);
		System.out.println(operation(100, (x) -> (x - 1)));
	}
	public Integer operation(Integer num, MyFun<Integer> mf) {
		return mf.getValue(num);
	}

	List<User> users = Arrays.asList(
			new User("gc", 24, 7500),
			new User("gc", 25, 13000),
			new User("gc", 26, 20000));
	
	@Test
	public void test6() {
		Collections.sort(users, (u1, u2) -> {
			return u1.getAge() - u2.getAge();
		});
		System.out.println(users);
	}
	@Test
	//对两个long型进行处理
	public void test7() {
		//参数3是lambda，用于实现函数式接口
		op(100L, 200L, (x,y) -> x + y);
	}
	public void op(Long t1, Long t2, MyFun2<Long, Long> mf2) {
		System.out.println(mf2.getValue(t1, t2));
	}
	@Test
	public void test() {
		users.stream().
			filter((e) -> e.getSalary() >= 10000).forEach(System.out::println);
		users.stream().
			map((e) -> e.getName()).forEach(System.out::println);
	}

}
















