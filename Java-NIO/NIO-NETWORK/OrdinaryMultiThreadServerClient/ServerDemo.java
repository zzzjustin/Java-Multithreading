package com.justin.multithreadserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadServerDemo {
	// 服务器端中实现多线程的线程池
	private static ExecutorService threadPool = Executors.newCachedThreadPool();
	
	//监听读取客户端数据类
	static class ReadClientData implements Runnable {
		Socket clientSocket; // 获取该客户端连接
		
		//构造方法初始化
		public ReadClientData(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}
		
		@Override
		public void run() {
			BufferedReader bufferedRead = null;
			PrintWriter printWrite = null;
			InputStreamReader streamRead = null;
			String readData = null; //保存每读到一行的数据
			
			try {
				streamRead = new InputStreamReader(clientSocket.getInputStream());
				bufferedRead = new BufferedReader(streamRead);
				printWrite = new PrintWriter(clientSocket.getOutputStream());
				
				//开始读客户端发来的数据
				while ((readData = bufferedRead.readLine()) != null) {
					printWrite.println(readData); //输出读到的数据
				}
				//回发客户端确认连接信息
				printWrite.write("建立连接成功.\r\n");
				printWrite.flush(); // 将缓冲区的数据输出,刷新缓冲区
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(bufferedRead != null) {
						bufferedRead.close();
					}
					if(printWrite != null) {
						printWrite.close();
					}
					clientSocket.close(); //关闭客户端连接
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@SuppressWarnings("resource")
	//服务器中的主线程
	public static void main(String[] args) { 
		ServerSocket server = null;
		Socket clientSocket = null;
		try {
			server = new ServerSocket(5000); //端口号5000
		} catch (IOException e) {
			e.printStackTrace();
		}

		//开始死循环监听客户端发来的请求
		while (true) {
			try {
				clientSocket = server.accept(); //accept()方法会阻塞当前线程,等待客户端发来的请求
				System.out.println(clientSocket.getRemoteSocketAddress() + " succeed connect.");
				ReadClientData readThread = new ReadClientData(clientSocket); // 为这个客户端连接开启读数据线程
				threadPool.execute(readThread); // 为该客户端服务的线程提交到线程池执行
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
