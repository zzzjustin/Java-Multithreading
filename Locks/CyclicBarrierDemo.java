package cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.Random;

public class CyclicBarrierDemo {
	
	public static class Student implements Runnable {
		//实例化循环栅栏
		private final CyclicBarrier cyclic;
		
		//构造函数初始化
		Student(CyclicBarrier cyclic) {
			this.cyclic = cyclic;
		}
		
		//模拟线程到达
		void ThreadReady() {
			try {
				Thread.sleep(Math.abs(new Random().nextInt()%10000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+" ready.");
		}
		//模拟线程工作
		void ThreadStart() {
			try {
				Thread.sleep(Math.abs(new Random().nextInt()%10000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+" complete");
		}
		
		@Override
		public void run() {
			try {
				ThreadReady(); //所有线程开始到达
				cyclic.await(); //等待所有线程到达
				ThreadStart(); //所有线程开始运行
				cyclic.await(); //等待所有线程完成
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class BarrierRun implements Runnable {
		boolean flag;
		int i;
		//构造函数初始化
		BarrierRun(boolean flag, int i) {
			this.flag = flag;
			this.i = i;
		}
		
		@Override
		public void run() {
			if(flag) {
				System.out.println("Thread "+i+"个,所有线程完成.");
			} else {
				System.out.println("Thread "+i+"个,线程到达.");
				flag = true;
			}
		}
	}
	
	public static void main(String[] args) {
		boolean flag = false;
		int i =10;
		Thread arr[] = new Thread[i];
		BarrierRun br = new BarrierRun(flag, i);
		/*CyclicBarrier的构造函数:public CyclicBarrier(int parties, Runnable barrierAction)
		 * 第一个参数parties是计数个数,第二个参数是当计数器一次计数完成后,系统会执行的动作.
		 */
		CyclicBarrier cyclic = new CyclicBarrier(i, br);
		for(int j=0; j<i; j++) {
			arr[j] = new Thread(new Student(cyclic));
			arr[j].start();
		}

	}

}
