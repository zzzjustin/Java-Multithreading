package producerconsumer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class ConsumerDemo implements Runnable {
	private BlockingQueue<work> workQueue; //内存缓冲区队列
	private volatile boolean isRunning = true; //标识当前线程的状态
	
	//构造函数初始化
	public ConsumerDemo(BlockingQueue<work> workQueue) {
		this.workQueue = workQueue;
	}
	
	public void stopConsumer() {
		this.isRunning = false;
	}
	
	@Override
	public void run() {
		work takeWork;
		Random r = new Random();
		
		System.out.println("消费者线程: "+Thread.currentThread().getId()+"开始执行.");
		while(isRunning) {
			try {
				takeWork = workQueue.take(); //从缓冲区中获取任务
				if(takeWork != null) {
					System.out.println("消费者线程: "+Thread.currentThread().getId()+"获取任务:"+takeWork.getData());
				} else {
					System.out.println("缓冲区空.");
				}
				Thread.sleep(r.nextInt(1000));
			} catch(InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt(); 
			}
		}
	}
	
}
