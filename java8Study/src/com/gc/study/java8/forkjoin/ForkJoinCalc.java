package com.gc.study.java8.forkjoin;

import static org.junit.Assert.*;

import java.util.concurrent.RecursiveTask;

import org.junit.Test;

public class ForkJoinCalc extends RecursiveTask<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 728788512663507131L;
	
	private long start;
	private long end;
	
	private static final long THRESHOLD = 10000;
	
	
	public ForkJoinCalc(long start, long end) {
		this.start = start;
		this.end = end;
	}


	@Override
	protected Long compute() {
		long length = end - start;
		if(length <= THRESHOLD) {
			long sum = 0;
			for (long i = start; i <= end; i++) {
				sum += i;
			}
			return sum;
		} else {
			long mid = (start + end) / 2;
			ForkJoinCalc left = new ForkJoinCalc(start, mid);
			left.fork(); 	//拆分子任务，同时压入线程队列
			ForkJoinCalc right = new ForkJoinCalc(mid+1, end);
			right.fork();
			
			return left.join() + right.join();
		}
	}

}
