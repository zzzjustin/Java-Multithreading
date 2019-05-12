package atomicstampedreference;

import java.util.concurrent.atomic.AtomicReference;

public class UnSafeAtomicReference {

	static AtomicReference<Integer> flow = new AtomicReference<Integer>();

	public static void main(String[] args) {
		flow.set(45); //初始流量为45M
		
		//模拟服务提供商赠送
		for(int i=0; i<20; i++) {
			Thread T1 = new Thread(new Runnable() {
				@Override
				public void run() {
					while(true) {
						for(;;) {
							Integer f = flow.get();
							if(f<50) {
								System.out.println("剩余流量小于50M,赠送100M....");
								if(flow.compareAndSet(f, f+100)) {
									System.out.println("流量充值成功!当前流量剩余:"+flow.get());
									break;
								}
							} else {
								//System.out.println("剩余流量大于50M,不参与赠送活动.");
								break;
							}
						}
					}	
				}
			});
			T1.start();
		}
		
		//模拟消耗流量
		for(int i=0; i<20; i++) {
			Thread T2 = new Thread(new Runnable() {
				@Override
				public void run() {
					while(true) {
						Integer f = flow.get();
						if(f>50) {
							System.out.println("剩余流量大于50M,使用流量....");
							if(flow.compareAndSet(f, f-25)) {
								System.out.println("流量使用25M,当前流量剩余:"+flow.get());
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
