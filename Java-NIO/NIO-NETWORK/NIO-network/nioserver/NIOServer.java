package com.justin.nioserver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NIOServer implements Runnable {
	private ExecutorService serverThreadPool = Executors.newCachedThreadPool();
	private Selector selector; //管理Channel的选择器
	
	private void openServer() throws IOException {
		// ServerSocketChannel监听新连接进来的客户端连接,和ServerSocket一样
		ServerSocketChannel server = ServerSocketChannel.open();
		server.configureBlocking(false); //设置为非阻塞式
		selector = Selector.open(); //创建选择器
		
		InetSocketAddress address = new InetSocketAddress(InetAddress.getLocalHost(), 5000);
		server.bind(address); // bind()方法绑定本地主机
		//通道绑定选择器,注册接收连接事件
		SelectionKey acceptKey = server.register(selector, SelectionKey.OP_ACCEPT);
		
		while (true) {
			selector.select(); //select()方法返回选择器selector中那些符合你注册的事件的通道
			Set acceptKeySet = selector.selectedKeys(); //获得“键集”,由selector管理的通道中的就绪通道
			Iterator iterator = acceptKeySet.iterator(); //迭代器遍历通道集合
			while (iterator.hasNext()) {
				SelectionKey availableKey = (SelectionKey) iterator.next();
				
				if(availableKey.isValid() && availableKey.isAcceptable()) {
					System.out.println("Search scceptable channel success!");
					new serverAccept().Accept(selector, availableKey);
				} else if(availableKey.isValid() && availableKey.isWritable()) {
					System.out.println("Search writable channel success!");
					new serverWrite().Write(selector, availableKey);
				} else if(availableKey.isValid() && availableKey.isReadable()) {
					System.out.println("Search readable channel success!");
					new serverRead().Read(selector, availableKey);
				}
				iterator.remove();
			}
		}
		
	}
	@Override 
	public void run() {
		System.out.println("Start server.");
		NIOServer server = new NIOServer();
		try {
			server.openServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		Thread serverThread = new Thread(new NIOServer());
		serverThread.start();
	}
	
}
