import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class ReentrantLockCondition implements Runnable {
	public static ReentrantLock lock = new ReentrantLock();
	public static Condition condition = lock.newCondition();
	
	@Override
	public void run() {
		try {
			lock.lock();
			System.out.println("Thread is waiting");
			condition.await(); //线程等待
			System.out.println("Thread is going on");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	public static void main(String[] args) throws InterruptedException {
		ReentrantLockCondition t1 = new ReentrantLockCondition();
		Thread T1 = new Thread(t1); //执行t1对象线程同时关注它的锁
		T1.start();
		Thread.sleep(5000); //睡眠5秒后唤醒T1
		lock.lock();
		condition.signal();
		lock.unlock();
	}

}
