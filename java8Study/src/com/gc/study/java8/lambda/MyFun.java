package com.gc.study.java8.lambda;

@FunctionalInterface
public interface MyFun<T> {
	
	public T getValue(T value);
}
