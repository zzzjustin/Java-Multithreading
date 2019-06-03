package com.justin.parallelsearch;

public class SearchMethod {
	//顺序查找方法
	public static int search(int startPosition, int endPosition, int searchNum) {
		for(int i=startPosition; i<endPosition; i++) {
			//先检查下结果集result,如果其他线程已经率先找到这个searchNum,就直接返回结果
			if(MainSearchDemo.result.get() == searchNum) {
				return MainSearchDemo.result.get();
			}
			//否则就继续搜索
			if(MainSearchDemo.arr[i] == searchNum) {
				//如果查找成功,更新结果集
				if(!MainSearchDemo.result.compareAndSet(-1, i)) {
					//如果更新失败,表明有其他线程率先找到下标并设置结果集成功,同样直接返回结果即可
					return MainSearchDemo.result.get();
				} else {
					System.out.println("线程" + Thread.currentThread().getName() + "查找成功!下标为:" + i);
				}
				return i; //查找成功,返回下标
			}
		}
		return -1; //查找失败,返回-1
	}
}
