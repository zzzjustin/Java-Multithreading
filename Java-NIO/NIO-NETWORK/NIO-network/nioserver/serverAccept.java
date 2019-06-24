package com.justin.nioserver;

import java.net.InetAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class serverAccept {
	
	protected void Accept(Selector selector, SelectionKey availableKey) {
		// 首先获得服务器通道
		ServerSocketChannel server = (ServerSocketChannel) availableKey.channel();
		SocketChannel clientChannel; // 客户端连接
		
		try {
			clientChannel = server.accept(); // 监听客户端连接请求,返回的通道就是客户端和服务器通信的通道
			clientChannel.configureBlocking(false); // 设置为非阻塞式
			
			// 这个客户端连接的感兴趣事件是读事件
			SelectionKey clientKey = clientChannel.register(selector, SelectionKey.OP_READ);
			// 为每一个客户端连接分配一个通信用的数据队列
			DataQueue dataQueue = new DataQueue();
			if(clientChannel != null) {
				clientKey.attach(dataQueue); // 通信数据队列附加到这个客户端上
			}
			InetAddress clientAddress = clientChannel.socket().getInetAddress();
			System.out.println("Accepted connection from " + clientAddress.getHostAddress() + ".");
//			availableKey.cancel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
