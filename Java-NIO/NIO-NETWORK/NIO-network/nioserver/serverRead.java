package com.justin.nioserver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class serverRead {
	protected void Read(Selector selector, SelectionKey availableKey) throws IOException {
		SocketChannel channel = (SocketChannel) availableKey.channel();
		ByteBuffer dataByte = ByteBuffer.allocate(1024);
		
		System.out.print("Accept messages from client:\r\n");
		try {
			int dataLength = channel.read(dataByte);
			while (dataLength != 0) {
				System.out.println("content length: " + dataLength);
				dataByte.flip(); // 方法改变读写指针的位置,从0开始				
				// hasRemaining()方法返回Buffer中剩余的可用长度
				while (dataByte.hasRemaining()) {
					System.out.print((char)dataByte.get());
				}		
				System.out.println("\r\n");
				dataByte.clear(); //清空缓冲区
				dataLength = channel.read(dataByte);
			}
			availableKey.interestOps(SelectionKey.OP_READ); //读完数据后,将读事件移除,防止一直重复读
			channel.register(selector, SelectionKey.OP_WRITE); //注册写事件
//			availableKey.cancel();
		} catch (Exception e) {
			System.out.println("Error to read.");
			e.printStackTrace();
			availableKey.channel().close();
			return;
		}
	}
}
