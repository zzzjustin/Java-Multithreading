package fairlock;

import java.util.concurrent.locks.ReentrantLock;

public class FairLock implements Runnable {

	//实例化一把公平锁,利用ReentrantLock的构造函数
	public static ReentrantLock Lock = new ReentrantLock();
	
	@Override
	public void run() {
		while (true) {
			try {
				Lock.lock();
				System.out.println("线程 "+Thread.currentThread().getName()+"获得锁.");
			} 
			finally {
				Lock.unlock();
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		FairLock L1 = new FairLock();
		Thread T1 = new Thread(L1,"T1");
		Thread T2 = new Thread(L1,"T2");
		Thread T3 = new Thread(L1,"T3");
		T1.start();
		T2.start();
		T3.start();
	}

}
