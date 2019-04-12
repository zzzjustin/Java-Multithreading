package readwrite_lock;

import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.Random;

public class ReadWriteLock {
	
	//实例化读写锁
	private static ReentrantReadWriteLock readwriteLock = new
			ReentrantReadWriteLock();
	private static Lock readLock = readwriteLock.readLock();
	private static Lock writeLock = readwriteLock.writeLock();
	
	private int value = 0;
	
	
	//读线程
	public Object handleRead(Lock lock) throws InterruptedException {
		try {
			lock.lock();
			Thread.sleep(1000);
			System.out.println(value);
			return value; //读出value值
		} finally {
			lock.unlock();
		}
	}
	
	//写线程
	public void handleWrite(Lock lock, int tmp) throws InterruptedException {
		try {
			lock.lock();
			Thread.sleep(1000);
			System.out.println("value写入="+tmp);
			value = tmp;
		} finally {
			lock.unlock();
		}
	}
	
	public static void main(String[] args) {
		final ReadWriteLock RWL = new ReadWriteLock();
		Thread RT1 = new Thread(new Runnable() {
			@Override
			public void run () {
				try {
					RWL.handleRead(readLock);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		Thread WT1 = new Thread(new Runnable() {
			@Override
			public void run () {
				try {
					RWL.handleWrite(writeLock, new Random().nextInt());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		for (int k=0; k<2; k++) {
			new Thread(WT1).start();
		}
		
		for (int i=0; i<10; i++) {
			new Thread(RT1).start();
		}
	}

}
