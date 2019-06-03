package com.justin.parallelsearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class MainSearchDemo {
	public static int[] arr;
	public static AtomicInteger result = new AtomicInteger(); //存放查找结果
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		int startPosition, endPosition, searchNum;
		arr = new int[100];
		int arrayLength = arr.length / 5; //按照线程数等分数组
		Scanner in = new Scanner(System.in);
		Random rd = new Random();
		
		ExecutorService threadPool = Executors.newCachedThreadPool(); //线程池
		result.set(-1); // 初始结果集中下标初始化为-1
		
		System.out.println("数组元素:");
		for(int i=1; i<=100; i++) {
			arr[i-1] = rd.nextInt(1000);
			System.out.print(arr[i-1] + " ");
			if((i%10) == 0) {
				System.out.print("\r\n");
			}
		}
		
		List<Future<Integer>> resultQueue = new ArrayList<Future<Integer>>();
		
		System.out.print("\r\n请输入要查找的值:");
		searchNum = in.nextInt();

		for(startPosition=0; startPosition<arr.length; startPosition+=arrayLength) {
			endPosition = startPosition + arrayLength;
			if(endPosition > arr.length) {
				//如果末尾位置越界
				endPosition = arr.length;
			}
			//查找
			SearchThread searchThread = new SearchThread(startPosition, endPosition, searchNum);
			resultQueue.add(threadPool.submit(searchThread));
		}
    
		in.close();
	}

}
