package lock_support;

import java.util.Random;
import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo implements Runnable {

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName()+"开始.");
		try {
			//线程睡眠,模拟执行.
			Thread.sleep(Math.abs(new Random().nextInt()%10000));
			
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		//线程阻塞
		LockSupport.park();
		System.out.println(Thread.currentThread().getName()+"执行完毕.");
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread T1 = new Thread(new LockSupportDemo(), "Thread-1");
		Thread T2 = new Thread(new LockSupportDemo(), "Thread-2");
		T1.start();
		T2.start();
		Thread.sleep(3000);
		LockSupport.unpark(T1);
		LockSupport.unpark(T2);
		T1.join();
		T2.join();

	}

}
