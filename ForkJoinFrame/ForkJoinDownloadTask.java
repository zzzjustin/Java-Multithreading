package forkjoinframe;

import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

public class ForkJoinDownloadTask extends RecursiveTask<Long> {
	
	private static final long serialVersionUID = 1L;
	private long lowerBound; //起始字节
	private long upperBound; //结束字节
	private long dataSum; //(子)任务数据量
	private long sum = 0;
	
	public ForkJoinDownloadTask(long lowerBound, long upperBound) {
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}
	
	@Override
	protected Long compute() {
		
		if((upperBound-lowerBound)<10000) {
			for(long i=lowerBound; i<=upperBound; i++) {
				sum +=i; //模拟下载数据
			}
		} else {
			//如果文件太大,就进行分割处理
			long part = (upperBound-lowerBound)/100; //记录分割成100部分,每部分文件的大小
			long startLocation = lowerBound; //initLocation记录任务的初始开始位置
			//存放这100个子任务的队列
			ArrayList<ForkJoinDownloadTask> downloadTask = new ArrayList<ForkJoinDownloadTask>(); 
			
			//把分成的100个子任务都提交到队列里去
			for(int i=0; i<100; i++) {
				long endLocation = startLocation+part; //记录该子任务的下载结束位置
				//特殊情况判断,如果该子任务的结束位置大于总任务的结束位置,即当前子任务是最后一个了.
				if(endLocation>upperBound) {
					//让子任务的结束位置等于总任务大小的结束位置.
					endLocation = upperBound;
				}
				
				//实例化子任务
				ForkJoinDownloadTask downloadtask = new ForkJoinDownloadTask(startLocation, endLocation);
				//输出子任务的信息
				downloadtask.dataSum = downloadtask.upperBound - downloadtask.lowerBound; //该子任务的下载数据量
				sum+=downloadtask.dataSum; //sum保存数据总量
				System.out.println("Task - "+i+"： startLocation="+downloadtask.lowerBound+", "
						+ "endLocation="+downloadtask.upperBound);
				System.out.println("dataSum="+downloadtask.dataSum+"  sum="+sum+"\r\n");
								
				startLocation+=part; //下一个子任务的开始位置
				downloadTask.add(downloadtask); //把当前子任务存入任务队列
				downloadtask.fork(); //为子任务创建分支
			}
			//所有子任务提交到任务队列完成
			
			for(ForkJoinDownloadTask t:downloadTask) {
				t.join(); //join()等待任务,即"等待子任务下载完成"
			}
		}
		
		return sum;
	}
}
