package threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalDemo {
	public static ThreadLocal<ThreadLocalDemo> v = new ThreadLocal<ThreadLocalDemo>();
	private int value = 0;
	public static int sum = 0;
	
	public void increase() {
		value++;
	}
	
	public static class IncreaseValue implements Runnable {
		@Override
		public void run() {
			if(v.get()==null) {
				v.set(new ThreadLocalDemo());
			}
			ThreadLocalDemo TL = v.get();
			
			for(int i=0; i<10; i++) {
				TL.increase();
				System.out.println("In "+Thread.currentThread().getName()+" value = "+TL.value);
			}
			ThreadLocalDemo.sum += TL.value;
			System.out.println("sum = "+sum);
		}
	}

	public static void main(String[] args) {
		IncreaseValue TLD = new IncreaseValue();
		ExecutorService es = Executors.newFixedThreadPool(10);
		for(int i=0; i<10; i++) {
			es.execute(TLD);
		}

		es.shutdown();
	}

}
