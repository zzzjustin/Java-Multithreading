package definethreadpoolexecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

public class DefineThreadPoolExecutorDemo implements Runnable {

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName()+" is running. ThreadId = "+Thread.currentThread().getId());
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		} 
		System.out.println(Thread.currentThread().getName()+" finish.");
	}
	
	public static void main(String[] args) throws InterruptedException{
		DefineThreadPoolExecutorDemo threadpoolDemo = new DefineThreadPoolExecutorDemo();
		
		/*新建线程池,并自定义其参数
		 * 线程数为5;最大线程数为5;线程池线程数超过corePoolSize时,多余空闲线程存活时间为0;使用的是无界队列;
		 * 最后一个参数重写ThreadFactory里的newThread方法,让线程创建时输出自己的线程Id.
		 */
		ExecutorService es = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(),
				new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				System.out.println("创建线程："+t.getId());
				return t;
			}
		});
		
		for(int i=0; i<10; i++) {
			es.submit(threadpoolDemo);
		}
		
		Thread.sleep(5000);
		es.shutdown();
	}

}
