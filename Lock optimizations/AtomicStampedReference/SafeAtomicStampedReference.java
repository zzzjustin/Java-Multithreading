package atomicstampedreference;

import java.util.concurrent.atomic.AtomicStampedReference;

public class SafeAtomicStampedReference {

	static AtomicStampedReference<Integer> flow = new AtomicStampedReference<Integer>(45, 0);
	
	public static void main(String[] args) {
		//模拟服务提供商赠送
		for(int i=0; i<1; i++) {
			final int time = flow.getStamp();
			Thread T1 = new Thread(new Runnable() {
				@Override
				public void run() {
					while(true) {
						for(;;) {
							Integer f = flow.getReference();
							 
							if(f<50) {
								//System.out.println("剩余流量小于50M,赠送100M....");
								if(flow.compareAndSet(f, f+100, time, time+10)) {
									System.out.println("流量充值成功!当前流量剩余:"+flow.getReference());
									break;
								}
							} else {
								break;
							}
						}
					}	
				}
			});
			T1.start();
		}
		
		for(int i=0; i<20; i++) {
			Thread T2 = new Thread(new Runnable() {
				@Override
				public void run() {
					while(true) {
						final int time = flow.getStamp();
						Integer f = flow.getReference();
						if(f>50) {
							System.out.println("剩余流量大于50M,使用流量....");
							if(flow.compareAndSet(f, f-25, time, time+10)) {
								System.out.println("流量使用25M,当前流量剩余:"+flow.getReference());
								break;
							}
						} else {
							System.out.println("剩余流量不足50M,无法使用.");
							break;
						}
						try {
							Thread.sleep(200);
						} catch(InterruptedException e) {
							e.printStackTrace();
						}
					}	
				}
			});
			T2.start();
		}
		
	}

}
