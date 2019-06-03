package com.justin.parallelsearch;

import java.util.concurrent.Callable;

public class SearchThread implements Callable<Integer> {
	int startPosition, endPosition, searchNum;
	//构造方法初始化
	public SearchThread(int startPosition, int endPosition, int searchNum) {
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.searchNum = searchNum;
	}
	
	public Integer call() {
		//调用顺序查找方法
		int searchResult = SearchMethod.search(startPosition, endPosition, searchNum);
		return searchResult; //返回搜索结果
	}
}
