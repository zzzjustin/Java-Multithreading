package com.justin.parallelpipeline;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SingleThread {

	public static void main(String[] args) {
		double num1, num2;
		BlockingQueue<Data> dataQueue = new LinkedBlockingQueue<Data>();
		long startTime = System.currentTimeMillis();
		
		Thread singleThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						Data data = dataQueue.take();
						data.D = (data.A + data.B) / data.C - data.D;
						System.out.println(data.resule + data.D);
						if(data.A == 999) {
							System.out.println("consumeTime = " + (System.currentTimeMillis()-startTime) + "ms");
							break;
						}
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		singleThread.start();
		
		for(int i=1; i<1000; i++) {
			for(int j = 1; j<1000; j++) {
				num1 = i;
				num2 = j;
				Data data = new Data(num1, num2, 2*num1, 2*num2);
				data.resule = "(" + num1 + " + " + num2+") / " + 2*num1 + " - " + 2*num2 + " = ";
				dataQueue.add(data);
			}
		}
	}

}
