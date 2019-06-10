package com.justin.futurecallable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureCallableMain {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//创建Future<String>泛型接口队列,你也可以直接用一个泛型接口来接收
		List<Future<String>> resultQueue = new ArrayList<Future<String>>();
		Future<String> result;
		
		//创建线程池
		ExecutorService threadpool = Executors.newCachedThreadPool();
		//创建任务
		DataCreatorDemo dataCreator1 = new DataCreatorDemo(" IN YOUR AREA.");
		DataCreatorDemo dataCreator2 = new DataCreatorDemo(" IS THE REVOLUTION.");
		//任务提交到线程池
		
		//泛型接口队列接收线程返回值
		resultQueue.add(threadpool.submit(dataCreator1));
		//用泛型接口来接收
		result = threadpool.submit(dataCreator2);
		//此时主线程可以做其他事情,为了模拟显示,让主线程睡眠2秒后输出语句
		try {
			Thread.sleep(1000);
			System.out.println("MainThread do sth else....");
			Thread.sleep(1000);
			System.out.println("MainThread comeback.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//最后拿出两个线程中的数据
		System.out.println(resultQueue.get(0).get());
		System.out.println(result.get());
		threadpool.shutdown();
	}

}
