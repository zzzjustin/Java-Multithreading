package com.justin.nioclient;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class clientRead {
	protected void Read(Selector selector, SelectionKey availableKey) throws IOException {
		SocketChannel channel = (SocketChannel) availableKey.channel();
		ByteBuffer dataByte = ByteBuffer.allocate(1024);
		
		//读数据到缓冲区Buffer中
		int readData = channel.read(dataByte);
		while (readData != -1) {
			System.out.println("Accept messages from server: " + readData);
			dataByte.flip(); // 方法改变读写指针的位置,从0开始				
			while (dataByte.hasRemaining()) {
				System.out.print((char)dataByte.get());
			}		
			System.out.println("\r\n");
			dataByte.clear(); //清空缓冲区
			readData = channel.read(dataByte);
		}

		availableKey.interestOps(SelectionKey.OP_READ); 
		channel.close();
		availableKey.cancel();
	}
}
