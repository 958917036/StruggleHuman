package com.gc.study.java8.lambda;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.junit.Test;

/**
 * @author gc
 * 方法引用：若lambda体中的内容有方法已经实现了，我们可以使用“方法引用”
 * 		（可以理解为方法引用是lambda表达式的另外一种表达形式）
 * 主要有三种语法格式：
 * 		对象::实例方法名
 * 		类::静态方法名
 * 		类::实例方法名
 * 注意：
 * 	1.lambda体中调用方法的参数列表与返回值类型，要与函数式接口中抽象方法的参数列表和返回值类型一致
 *  2.若lambda参数列表 中的第一个参数是实例方法的调用者，而第二个参数是实例方法的参数时，可以使用className::method
 *  
 *  二、构造器引用
 *  格式：
 *  	ClassName::new
 */
public class TestMethodRef {

	
	//对象::实例方法名
	@Test
	public void test() {
		Consumer<String> consuemr = (x) -> System.out.println(x);
		consuemr.accept("aaaa");
		
		PrintStream ps = System.out;
		Consumer<String> consumer2 = ps::println;
		consumer2.accept("asdf");
	}
	
	@Test
	public void test2() {
		User user= new User("gc",20,200000);
		Supplier<String> sup = () -> user.getName();
		System.out.println(sup.get());
		
		Supplier<Integer> sup2 = user::getAge;
		System.out.println(sup2.get());
	}
	
	//类::静态方法名
	public void test3() {
		Comparator<Integer> com = (x,y) -> Integer.compare(x, y);
		
		Comparator<Integer> com1 = Integer::compare;
	}
	
	//类::实例方法名
	public void test4() {
		BiPredicate<String, String> bp = (x,y) -> x.equals(y);
		
		BiPredicate<String, String> bp2 = String::equals;
	}
	
	@Test
	public void test5() {
		Supplier<User> sup = () -> new User();
		
		Supplier<User> sup2 = User::new;
		System.out.println(sup2.get());
	}

}
