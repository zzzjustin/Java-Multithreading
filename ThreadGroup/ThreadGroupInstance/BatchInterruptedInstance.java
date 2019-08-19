package threadgroupinstance;

public class BatchInterruptedInstance implements Runnable {
	@Override
	public void run() {
		try {
			while(!Thread.currentThread().isInterrupted()) {
				System.out.println(Thread.currentThread().getName() + " running.");
				Thread.sleep(2000);
			}
		} catch(InterruptedException e) {
			System.out.println(Thread.currentThread().getName() + "end.");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ThreadGroup threadGroup = new ThreadGroup("MyThreadGroup");
		
		try {
			for(int i=0; i<4; i++) {
				BatchInterruptedInstance instance = new BatchInterruptedInstance();
				Thread T = new Thread(threadGroup, instance);
				T.start();
			}
			
			Thread.sleep(4000);
			// 中断线程组内所有线程
			threadGroup.interrupt(); 
			System.out.println("调用interrupt方法");
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
