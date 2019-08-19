package threadgroupinstance;

public class PrimaryCorrelation implements Runnable {
	@Override
	public void run() {
		try {
			while(true) {
				System.out.println(Thread.currentThread().getName() + "running....");
				Thread.sleep(5000);
			}
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ThreadGroup threadGroup = new ThreadGroup("MyThreadGroup");
		
		for(int i=0; i<3; i++) {
			PrimaryCorrelation runnableInstance = new PrimaryCorrelation();
			Thread thread = new Thread(threadGroup, runnableInstance);
			thread.start();
		}
		
		System.out.println("线程组名：" + threadGroup.getName());
		System.out.println("线程组中活跃的线程数：" + threadGroup.activeCount());
	}

}
