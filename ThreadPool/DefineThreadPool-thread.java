package definethreadpoolexecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DefineThreadPool implements Runnable {

	public String threadName;
	
	public DefineThreadPool(String threadName) {
		this.threadName = threadName;
	}
	
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
	
	public static void main(String[] args) throws InterruptedException {
		
		ExecutorService es = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>()) {
			@Override
			//重写线程池运行任务前动作
			protected void beforeExecute(Thread t, Runnable r) {
				System.out.println("线程 "+((DefineThreadPool) r).threadName+" 准备执行....");
			}
			
			@Override
			//重写线程池运行任务后动作
			protected void afterExecute(Runnable r, Throwable t) {
				System.out.println("线程 "+((DefineThreadPool) r).threadName+" 执行完成");
			}
			
			@Override
			//重写线程池结束动作
			protected void terminated() {
				System.out.println("线程池结束");
			}
		};

		for(int i=0; i<9; i++) {
			DefineThreadPool threadpool = new DefineThreadPool("Num-"+i);
			es.execute(threadpool);
			Thread.sleep(100);
		}
		es.shutdown();
	}

}
