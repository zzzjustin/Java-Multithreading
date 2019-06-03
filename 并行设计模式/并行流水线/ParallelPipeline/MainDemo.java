package com.justin.parallelpipeline;

public class MainDemo {
	public static void main(String[] args) {
		
		double num1, num2;
		//实例化三个步骤线程
		Thread additionThread = new Thread(new AdditionThread());
		Thread multiplyThread = new Thread(new MultiplyThread());
		Thread subtractionThread = new Thread(new SubtractionThread());
		additionThread.start();
		multiplyThread.start();
		subtractionThread.start();
		
		for(int i=1; i<1000; i++) {
			for(int j = 1; j<1000; j++) {
				num1 = i;
				num2 = j;
				Data data = new Data(num1, num2, 2*num1, 2*num2);
				data.resule = "(" + num1 + " + " + num2+") / " + 2*num1 + " - " + 2*num2 + " = ";
				//把数据放入第一步加法线程
				AdditionThread.dataQueue.add(data);
			}
		}
		
	}

}
