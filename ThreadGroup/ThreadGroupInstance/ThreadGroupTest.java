package threadgroupinstance;

public class ThreadGroupTest {

	public static void main(String[] args) {
		ThreadGroup threadGroup = new ThreadGroup("MyThreadGroup");
		
		System.out.println("线程组" + threadGroup.getName() + " 的父线程组为 ");
		System.out.println(threadGroup.getParent() + "\r\n");
		System.out.println("主线程的线程组名： " + Thread.currentThread().getThreadGroup().getName());
		System.out.println("主线程组中的子线程组数： " + Thread.currentThread().getThreadGroup().activeGroupCount());
		
		Thread T1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " running....");
			}
		});
		T1.setName("testThread");
		T1.start();
		
		// 复制取得主线程组
		ThreadGroup[] listThreadGroup = new ThreadGroup[Thread.currentThread().getThreadGroup().activeGroupCount()];
		Thread.currentThread().getThreadGroup().enumerate(listThreadGroup);
		System.out.println("主线程组中的子线程组名： " + listThreadGroup[0].getName() + "\r\n");
		
		 //复制取得主线程组中的线程集合
		Thread[] listThread = new Thread[Thread.currentThread().getThreadGroup().activeCount()];
		Thread.currentThread().getThreadGroup().enumerate(listThread);
		System.out.println("主线程组中的线程数： " + listThread.length);
		System.out.println("主线程组中的线程有： ");
		for(int i=0; i<listThread.length; i++) {
			System.out.println(listThread[i].getName());
		}
		
	}
}
