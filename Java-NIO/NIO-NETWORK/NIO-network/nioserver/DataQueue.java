package com.justin.nioserver;

import java.nio.ByteBuffer;
import java.util.LinkedList;

public class DataQueue {
	private LinkedList<ByteBuffer> dataQueue;
	
	DataQueue() {
		dataQueue = new LinkedList<ByteBuffer>();
	}
	
	// 数据队列写入
	public void writeByteBuffer(ByteBuffer data) {
		dataQueue.addFirst(data);
	}
	
	// 获取数据队列
	public LinkedList<ByteBuffer> getQueue() {
		return dataQueue;
	}
}
