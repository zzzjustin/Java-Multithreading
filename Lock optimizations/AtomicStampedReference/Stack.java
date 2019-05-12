package atomicstampedreference;

import java.util.concurrent.atomic.AtomicReference;

public class Stack {
	private AtomicReference<Node> stackTop = new AtomicReference<>();
	
	//栈的存储结构
	static class Node {
		int value;
		Node next; //指向下一个结点
		
		//构造函数初始化
		public Node(int value) {
			this.value = value;
		}
	}
	
	//无锁CAS入栈
	public void push(Node newNode) {
		//无锁CAS操作,所以用一个死循环
		for(;;) {
			Node tmpTop = stackTop.get(); //首先获得栈顶结点
			newNode.next = tmpTop; //新栈顶结点的next指向旧的栈顶结点
			
			//更新新的栈顶结点
			if(stackTop.compareAndSet(tmpTop, newNode)) {
				return;
			}
		}
	}
	
	//无锁CAS出栈
	public Node pop() {
		//同样死循环
		for(;;) {
			Node tmpTop = stackTop.get(); //获得栈顶结点
			//特殊情况判断
			if(tmpTop == null) {
				System.out.println("错误!栈顶元素为空.");
				return null;
			}
			Node newTop = tmpTop.next; //保存栈中的新栈顶结点
			
			//更新栈顶结点
			if(stackTop.compareAndSet(tmpTop, newTop)) {
				tmpTop.next = null;
				return tmpTop;
			}
		}
	}
	
}
