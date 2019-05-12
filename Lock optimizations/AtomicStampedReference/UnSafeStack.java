package atomicstampedreference;
 
public class UnSafeStack {

	public static void main(String[] args) {
		Stack stack = new Stack(); //实例化栈
		Stack.Node node1 = new Stack.Node(1);
		Stack.Node node2= new Stack.Node(2);
		Stack.Node node3= new Stack.Node(3);
		//三个结点入栈
		stack.push(node3);
		stack.push(node2);
		stack.push(node1);
		
		Thread T1 = new Thread(new Runnable() {
			@Override
			public void run() {
				Stack.Node tmp = stack.pop();
			}
		});
		
		Thread T2 = new Thread(new Runnable() {
			@Override
			public void run() {
				Stack.Node tmp1 = stack.pop();
				Stack.Node tmp2 = stack.pop();
				stack.push(tmp1);
			}
		});
		
		T1.start();
		T2.start();
	}

}
