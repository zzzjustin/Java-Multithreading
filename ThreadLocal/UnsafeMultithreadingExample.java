package threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InitDemo implements Runnable {
	private int value;
	
	public void increase() {
		value++;
	}
	
	@Override
	public void run() {
		for(int i=0; i<10000; i++) {
			increase();
			System.out.println("In "+Thread.currentThread().getName()+" value = "+value);
		}
	}

	public static void main(String[] args) {
		InitDemo ID = new InitDemo();
		ExecutorService es = Executors.newFixedThreadPool(10);
		for(int i=0; i<10; i++) {
			es.execute(new Thread(ID));
		}

		es.shutdown();
	}

}
