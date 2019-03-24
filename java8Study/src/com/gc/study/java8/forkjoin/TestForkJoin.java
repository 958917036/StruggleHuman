package com.gc.study.java8.forkjoin;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

import org.junit.Test;

public class TestForkJoin {

	long n = 10000000000L;
	@Test
	public void test() {
		Instant start = Instant.now();
		ForkJoinPool pool = new ForkJoinPool();
		ForkJoinTask<Long> task = new ForkJoinCalc(0L, n);
		
		Long sum = pool.invoke(task);
		Instant end = Instant.now();
		System.out.println("sum1:"+sum+" ,"+Duration.between(start, end).toMillis());
	}
	
	/*
	 * 普通for
	 */
	@Test
	public void test2() {
		Instant start = Instant.now();

		long sum = 0;
		for (long i = 0; i <= n; i++) {
			sum += i;
		}
		Instant end = Instant.now();
		System.out.println("sum2:"+sum+" ,"+Duration.between(start, end).toMillis());
	}
	
	@Test
	public void test3() {
		
		Instant start = Instant.now();

		long sum = LongStream.rangeClosed(0, n).parallel().reduce(0, Long::sum);
		Instant end = Instant.now();
		System.out.println("sum3:"+sum+" ,"+Duration.between(start, end).toMillis());
	}
}
