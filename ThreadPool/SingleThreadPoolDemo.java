package new_SingleThreadExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SingleThreadPoolDemo {
	
		public static class TestSingleThreadPool extends Thread {
		
		@Override
		public void run() {
			System.out.println("线程"+Thread.currentThread().getName()+"开始执行时间: "+System.currentTimeMillis());
			//线程睡眠,模拟执行
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//执行完成后输出时间
			System.out.println("线程"+Thread.currentThread().getName()+"结束执行时间: "+System.currentTimeMillis());
		}
	}
	public static void main(String[] args) {
		TestSingleThreadPool TP1 = new TestSingleThreadPool();
		//实例化线程池
		ExecutorService ES = Executors.newSingleThreadExecutor();
		for(int i=0; i<5; i++) {
			ES.submit(TP1);
		}
		ES.shutdown();
	}

}
