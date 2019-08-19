package threadgroupinstance;

public class RecursiveScanning {
	
	public static void main(String[] args) {
		// 获得主线程组
		ThreadGroup mainThreadGroup = Thread.currentThread().getThreadGroup();
		ThreadGroup threadGroup1 = new ThreadGroup(mainThreadGroup, "threadGroup1");
		ThreadGroup threadGroup2 = new ThreadGroup(mainThreadGroup, "threadGroup2");
		ThreadGroup threadGroup3 = new ThreadGroup(threadGroup1, "threadGroup3");
		
		ThreadGroup[] listThreadGroup = new ThreadGroup[Thread.currentThread().getThreadGroup().activeGroupCount()];
		// 非递归取得主线程祖中的子线程组
		Thread.currentThread().getThreadGroup().enumerate(listThreadGroup, false);
		
		System.out.println("非递归取出子线程组");
		for(int i = 0; i<listThreadGroup.length; i++) {
			if(listThreadGroup[i] != null) {
				System.out.println(listThreadGroup[i].getName());
			}
		}
		
	}

}
