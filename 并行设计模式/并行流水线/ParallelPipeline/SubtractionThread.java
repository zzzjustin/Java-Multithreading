package com.justin.parallelpipeline;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SubtractionThread implements Runnable {
	//创建一个队列存放除法线程传来的结果
	public static BlockingQueue<Data> subtractionResultQueue = new LinkedBlockingQueue<Data>();
	Data data = new Data();
	public static long endTime;
	
	//减法运算
	@Override
	public void run() {
		while(true) {
			try {
				data = subtractionResultQueue.take();
				data.D = data.C - data.D;
				System.out.println(data.resule + data.D);
				//达到最大计算次数后,通知另外两个线程停止
				if(data.A == 999) {
					MultiplyThread.isRunning = false; //除法线程停止
					AdditionThread.isRunning = false; //加法线程停止
					endTime = System.currentTimeMillis() - AdditionThread.startTime;
					System.out.println("consumeTime = " + endTime + "ms");
					break;
				}
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
