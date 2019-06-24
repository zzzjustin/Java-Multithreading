package com.justin.nioclient;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class clientConnect {
	protected void Connect(Selector selector, SelectionKey availableKey) throws IOException {
		SocketChannel channel = (SocketChannel) availableKey.channel();
		
		if(channel.isConnectionPending()) {
			System.out.println("connecting....");
			channel.finishConnect();
		}
		if(channel.isConnected()) {
			System.out.println("Successful connection!");
		}
		channel.configureBlocking(false);
		availableKey.interestOps(SelectionKey.OP_CONNECT); //移除连接事件
		channel.register(selector, SelectionKey.OP_WRITE); //注册写事件,向服务器发送信息
//		availableKey.cancel();
	}
	
}
