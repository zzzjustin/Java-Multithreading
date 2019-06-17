package com.justin.multithreadserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientDemo implements Runnable {
	Socket clientSocket = null;
	BufferedReader bufferedRead = null;
	PrintWriter printWrite = null;
	InputStreamReader streamRead = null;
	InetSocketAddress serverAddress = new InetSocketAddress("localhost", 5000); // 封装端口

	@Override
	public void run() {
		try {
			clientSocket = new Socket();
			clientSocket.connect(serverAddress);
			PrintWriter printWrite = new PrintWriter(clientSocket.getOutputStream());
			printWrite.println(clientSocket.getRemoteSocketAddress() + "send message.");
			//printWrite.flush(); // 将缓冲区的数据输出,刷新缓冲区
			
			streamRead = new InputStreamReader(clientSocket.getInputStream());
			bufferedRead = new BufferedReader(streamRead);
			System.out.println("Form server: " + bufferedRead.readLine());
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
				if(clientSocket != null) {
					clientSocket.close(); //关闭客户端连接
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		ClientDemo client = new ClientDemo();
		Thread clientThread = new Thread(client); 
		clientThread.start(); //启动客户端线程

	}

}
