package com.justinzeng.falsesharing;

public class FalseSharingDemo implements Runnable {
	//带包装的,有字节/无字节填充的Long数据类型数组
	private static LongDataPadding[] arr = new LongDataPadding[2];
	int index; 
	
	static {
		for(int i=0; i<2; i++) {
			arr[i] = new LongDataPadding();
		}
	}
	
	//构造函数初始化
	public FalseSharingDemo(int index) {
		this.index = index;
	}
	
	//有字节填充long数据类型
	public final static class LongDataPadding {
		public volatile long vaule = 0L;
		public long P1, P2, P3, P4, P5, P6, P7; //填充缓冲行
	}
	//无字节填充long数据类型
	public final static class LongDataNoPadding {
		public volatile long vaule = 0L;
	}
	
	@Override
	public void run() {
		for(long i=0; i<40000000L; i++) {
			arr[index].vaule = i;
		}
	}
	public static void main(String[] args) throws InterruptedException {
		Thread[] threads = new Thread[2];
		
		final long startTime = System.currentTimeMillis(); //记录开始时间
		for(int i=0; i<2; i++) {
			threads[i] = new Thread(new FalseSharingDemo(i));
		}
		for(Thread t : threads) {
			t.start();
		}
		for(Thread t : threads) {
			t.join();
		}
		System.out.println("consumeTime: "+(System.currentTimeMillis()-startTime)+"ms");
	}

}
