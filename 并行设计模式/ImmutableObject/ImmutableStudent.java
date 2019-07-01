package immutableobject;

// final关键字修饰类,保证该类不会有其他子类
public final class ImmutableStudent {
	// private修饰变量保证变量不会被其他对象获取,final修饰变量保证变量只在初始化时被赋值一次
	private final String name;
	private final String major;
	private final int age;
	private final String gender;
	
	public ImmutableStudent(String name, String major, int age, String gender) {
        this.name = name;
        this.major = major;
        this.age = age;
        this.gender = gender;
	}
	
	public ImmutableStudent(ImmutableStudent student) {
		this.name = student.getName();
		this.major = student.getMajor();
		this.age = student.getAage();
		this.gender = student.getGender();
	}
	
	public String getName() {
		return name;
	}
	
	public String getMajor() {
		return major;
	}
	
	public int getAage() {
		return age;
	}
	
	public String getGender() {
		return gender;
	}
}
