package com.justin.parallelpipeline;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MultiplyThread implements Runnable {
	//创建一个队列存放加法线程传来的结果
	public static BlockingQueue<Data> additionResultQueue = new LinkedBlockingQueue<Data>();
	Data data = new Data();
	public static boolean isRunning = true;
	
	//除法运算
	@Override
	public void run() {
		while(isRunning) {
			try {
				data = additionResultQueue.take();
				data.C = data.B / data.C;
				SubtractionThread.subtractionResultQueue.add(data);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
