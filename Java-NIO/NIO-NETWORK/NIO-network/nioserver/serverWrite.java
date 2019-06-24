package com.justin.nioserver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

public class serverWrite {
	protected void Write(Selector selector, SelectionKey availableKey) throws IOException {
		SocketChannel channel = (SocketChannel) availableKey.channel(); // 获得通道
		
		/* 你可以通过获得客户端和服务器端的通信数据交换队列来进行数据传输
		 *DataQueue dataQueue = (DataQueue) availableKey.attachment(); // 获得通讯队列
		 *LinkedList<ByteBuffer> queue = dataQueue.getQueue(); 
		 */
		LinkedList<ByteBuffer> queue = new LinkedList<ByteBuffer>();
		queue.add(ByteBuffer.wrap(new String("Hello client!").getBytes("UTF-8")));		
		ByteBuffer dataByte = queue.getLast();
		
		/* 你也可以用ByteBuffer直接给客户端发送数据
		 *ByteBuffer dataByte = ByteBuffer.allocate(1024);
		 *dataByte.clear();
		 *dataByte.put(ByteBuffer.wrap(new String("Hello client!").getBytes("UTF-8")));
		 *dataByte.flip();
		 */
		System.out.println("Send messages to the client...");
		try {
			int dataLength = channel.write(dataByte); //写入通道
			if(dataLength == -1) {
				availableKey.channel().close();
				return;
			}
			
			if(dataByte.remaining() == 0) {
				// Buffer缓冲区内的数据全部写完了
				queue.removeLast();
			}
		} catch (Exception e) {
			System.out.println("Error to write.");
			e.printStackTrace();
			availableKey.channel().close();
			return;
		}
		
		if(queue.size() == 0) {
			availableKey.interestOps(SelectionKey.OP_WRITE); // 所有数据写完后,将写事件移除
			channel.close();
			availableKey.cancel();
		}
	}
}
