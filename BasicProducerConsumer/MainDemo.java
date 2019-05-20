package producerconsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainDemo {

	public static void main(String[] args) throws InterruptedException {
		//创建缓冲区
		BlockingQueue<work> workQueue = new ArrayBlockingQueue<work>(10); //基于数组实现
		//创建线程池
		ExecutorService pcThreadPool = Executors.newCachedThreadPool(); //根据实际情况调整线程数量的线程池
		//创建生产者线程
		ProducerDemo producer1 = new ProducerDemo(workQueue);
		ProducerDemo producer2 = new ProducerDemo(workQueue);
		//创建消费者线程
		ConsumerDemo consumer1 = new ConsumerDemo(workQueue);
		ConsumerDemo consumer2 = new ConsumerDemo(workQueue);
		//将线程提交到线程池
		pcThreadPool.execute(producer1);
		pcThreadPool.execute(producer2);
		pcThreadPool.execute(consumer1);
		pcThreadPool.execute(consumer2);
		
		Thread.sleep(3*1000);
		producer1.stopProducer();
		producer2.stopProducer();
		consumer1.stopConsumer();
		consumer2.stopConsumer();
		
		Thread.sleep(5*1000);
		pcThreadPool.shutdown(); //关闭线程池
	}

}
