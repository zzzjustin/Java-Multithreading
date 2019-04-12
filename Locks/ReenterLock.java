import java.util.concurrent.locks.ReentrantLock;

public class ReenterLock implements Runnable {
	//ÊµÀý»¯Ò»¸öÖØÈëËø
	public static ReentrantLock lock = new ReentrantLock();
	public static int value = 0;
	@Override
	public void run() {
		for (int i=0; i<1000; i++) {
			lock.lock();
			lock.lock();
			try {
				value++;
				System.out.println("value="+value);
			} finally {
				lock.unlock();
				lock.unlock();
			}
		}
	}
		
	public static void main(String[] args) throws InterruptedException {
		ReenterLock L1 = new ReenterLock();
		Thread[] arr = new Thread[10];
		for (int i=0; i<10; i++) {
			arr[i] = new Thread(L1);
			arr[i].start();
		}
		for (int i=0; i<10; i++) {
			arr[i].join();
		}
//		Thread t1 = new Thread(L1);
//		Thread t2 = new Thread(L1);
//		t1.start();
//		t2.start();
//		t1.join();
//		t2.join();
		System.out.println(value);

	}

}
