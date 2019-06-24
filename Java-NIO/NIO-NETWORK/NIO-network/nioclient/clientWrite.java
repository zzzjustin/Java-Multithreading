package com.justin.nioclient;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class clientWrite {
	protected void Write(Selector selector, SelectionKey availableKey) throws IOException {
		SocketChannel channel = (SocketChannel) availableKey.channel();
		ByteBuffer dataByte = ByteBuffer.allocate(1024);
		dataByte.clear();
		dataByte.put(ByteBuffer.wrap(new String("Hello server!").getBytes("UTF-8")));
		dataByte.flip();
		
		if(channel.isConnected()) {
			channel.configureBlocking(false);
			System.out.println("Send information to the server...");
			while(dataByte.hasRemaining()) {
				channel.write(dataByte);
			}
			availableKey.interestOps(SelectionKey.OP_WRITE); //完成写操作后,移除写事件
			channel.register(selector, SelectionKey.OP_READ); //注册读事件,接收服务器发回来的信息
//			availableKey.cancel();
		}
	}
}
