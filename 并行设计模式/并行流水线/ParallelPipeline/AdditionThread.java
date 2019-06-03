package com.justin.parallelpipeline;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AdditionThread implements Runnable {
	//创建一个队列模拟接收从存储器读到值
	public static BlockingQueue<Data> dataQueue = new LinkedBlockingQueue<Data>();
	Data data = new Data();
	public static final long startTime = System.currentTimeMillis();
	public static boolean isRunning = true;
	
	//加法运算
	@Override
	public void run() {
		while(isRunning) {
			try {
				data = dataQueue.take(); //拿到数据
				data.B = data.A + data.B; //把A+B的结果保存到变量B中
				//把加法结果传到除法线程的队列中去
				MultiplyThread.additionResultQueue.add(data); 
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
