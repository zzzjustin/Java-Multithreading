package threadgroupinstance;

public class MultiCorrelation implements Runnable {
	@Override
	public void run() {
		try {
			while(true) {
				System.out.println(Thread.currentThread().getName() + " start.");
				Thread.sleep(5000);
			}
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// 获得主线程所在的线程组
		ThreadGroup mainThreadGroup = Thread.currentThread().getThreadGroup();
		// 创建一个新的线程组,线程组threadGroup的父线程组为mainGroup
		ThreadGroup threadGroup = new ThreadGroup(mainThreadGroup, "MyThreadGroup");
		
		// 创建线程放入到嵌套线程组中
		MultiCorrelation runnableInstance = new MultiCorrelation();
		Thread threadInstance = new Thread(threadGroup, runnableInstance);
		threadInstance.setName("MyThreadGroup - Thread1");
		threadInstance.start();
		
		ThreadGroup[] listThreadGroup = new ThreadGroup[Thread.currentThread().getThreadGroup().activeGroupCount()];
		// 主线程的线程组复制到listThreadGroup中
		Thread.currentThread().getThreadGroup().enumerate(listThreadGroup);
		
		System.out.println("线程组mainThreadGroup中的子线程组数： " + listThreadGroup.length);
		System.out.println("嵌套线程组名：" + listThreadGroup[0].getName());
		
		Thread[] listThread = new Thread[listThreadGroup[0].activeCount()];
		// 嵌套线程组中的活跃线程复制到listThread中
		listThreadGroup[0].enumerate(listThread);
		System.out.println("嵌套线程组中的活跃线程： " + listThread[0].getName());
	}

}
