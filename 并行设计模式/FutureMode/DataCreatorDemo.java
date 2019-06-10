package com.justin.futurecallable;

import java.util.concurrent.Callable;

public class DataCreatorDemo implements Callable<String> {
	private String dataString;
	//构造方法初始化
	public DataCreatorDemo(String dataString) {
		this.dataString = dataString;
	}
	
	@Override
	public String call() throws Exception {
		System.out.println("进入DataCreator线程" + Thread.currentThread().getName() + "....");
		long startTime = System.currentTimeMillis();
		StringBuffer BLACKPINK = new StringBuffer("BLACKPINK:");
		BLACKPINK.append(dataString);
		try {
			//线程睡眠模拟数据创建过程较长
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("数据创建完成,耗时: " + (System.currentTimeMillis()-startTime) + "ms");
		
		return BLACKPINK.toString(); //最后返回String类型结果出去
	}
}
