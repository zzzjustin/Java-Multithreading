package producerconsumer;

public final class work {
	private final int data;
	
	//构造函数初始化
	public work(int data) {
		this.data = data;
	}
	
	public work(String s) {
		this.data = Integer.valueOf(s);
	}
	
	public int getData() {
		return this.data;
	}
	
	@Override
	public String toString() {
		return "data = "+this.data;
	}
}
