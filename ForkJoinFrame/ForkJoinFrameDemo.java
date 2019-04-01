package forkjoinframe;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinFrameDemo {

	public static void main(String[] args) {
		ForkJoinPool forkjoinpool = new ForkJoinPool(); //ForkJoinPool线程池
		ForkJoinDownloadTask task = new ForkJoinDownloadTask(0, 300000L); //实例化下载任务
		ForkJoinTask<Long> taskResult = forkjoinpool.submit(task); //存放下载任务返回的结果
		
		try {
			long result = taskResult.get(); //获取每个子任务的下载结果
			System.out.println("completed! sum = "+result);
		} catch(InterruptedException e) {
			e.printStackTrace();
		} catch(ExecutionException e) {
			e.printStackTrace();
		}

	}

}
