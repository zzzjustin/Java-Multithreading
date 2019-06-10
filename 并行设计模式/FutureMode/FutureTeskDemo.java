package com.justin.futurecallable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureTeskDemo {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//创建线程池
		ExecutorService threadpool = Executors.newCachedThreadPool();
		//创建任务
		DataCreatorDemo dataCreator1 = new DataCreatorDemo(" IN YOUR AREA.");
		FutureTask<String> futuretesk = new FutureTask<String>(dataCreator1);
		threadpool.submit(futuretesk); //FutureTesk任务提交线程池执行
		
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
		System.out.println(futuretesk.get());
		threadpool.shutdown();
	}

}
