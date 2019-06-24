package com.justin.nioclient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOclient implements Runnable {
	private Selector selector;
	
	protected void initClient(int port, InetAddress ipAddress) throws IOException {
		// 配置要连接的远程服务器地址和端口号
		InetSocketAddress address = new InetSocketAddress(ipAddress, port); 
		SocketChannel channel = SocketChannel.open(); //打开通道
		channel.configureBlocking(false); // 设置为非阻塞式
		channel.connect(address); // connect()方法连接远程服务器
		this.selector = Selector.open(); // 创建选择器
		channel.register(selector, SelectionKey.OP_CONNECT); // 注册连接事件
	}
	
	protected void connectServer() throws IOException {
		while (true) {
			if(selector.isOpen() == false) {
				break;
			}
			selector.select(); //查找连接事件的通道
			Set connectKeySet = selector.selectedKeys();
			Iterator iterator = connectKeySet.iterator(); //迭代器遍历通道集合
			while (iterator.hasNext()) {
				SelectionKey availableKey = (SelectionKey) iterator.next();
				iterator.remove();
				
				if(availableKey.isConnectable()) {
					System.out.println("Search connectable channel success!");
					new clientConnect().Connect(selector, availableKey);
				} else if(availableKey.isValid() && availableKey.isWritable()) {
					System.out.println("Search writable channel success!");
					new clientWrite().Write(selector, availableKey);
				} else if(availableKey.isValid() && availableKey.isReadable()) {
					System.out.println("Search readable channel ssuccess!");
					new clientRead().Read(selector, availableKey);
				}
			}
		}
	}
	@Override
	public void run() {
		System.out.println("Start client.");
		NIOclient client = new NIOclient();
		try {
			client.initClient(5000, InetAddress.getLocalHost());
			client.connectServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		Thread clientThread = new Thread(new NIOclient());
		clientThread.start();
	}
}
