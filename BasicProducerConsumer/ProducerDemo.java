package producerconsumer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerDemo implements Runnable {
	private BlockingQueue<work> workQueue; //内存缓冲区队列
	private static AtomicInteger count = new AtomicInteger(); //队列中的总任务数
	private volatile boolean isRunning = true; //标识当前线程的状态
	
	//构造函数初始化
	public ProducerDemo(BlockingQueue<work> workQueue) {
		this.workQueue = workQueue;
	}
	
	public void stopProducer() {
		this.isRunning = false;
	}
	
	@Override
	public void run() {
		work newWork = new work(count.incrementAndGet());
		Random r = new Random();
		
		System.out.println("生产者线程: "+Thread.currentThread().getId()+"开始执行.");
		try {
			while(isRunning) {
				if(!workQueue.offer(newWork)) {
					System.out.println("生产者线程: "+Thread.currentThread().getId()+": 缓冲区满,任务-"+newWork+"放入失败.");
				} else {
					System.out.println("生产者线程: "+Thread.currentThread().getId()+"将任务-"+newWork+"放入缓冲区.");
				}
				Thread.sleep(r.nextInt(1000));
			}
		} catch(InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt(); 
		}
	}
	
}
