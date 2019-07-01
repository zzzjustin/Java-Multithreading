package immutableobject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;

public class ManagementSystem {
	private static volatile ManagementSystem managementSystem= new ManagementSystem();
	// 学号与学生对象实例之间的对应关系
	private final HashMap<String, ImmutableStudent> studentForm = new HashMap<String, ImmutableStudent>();
	public void Initial() {
		// 数据库操作
		Connection con = null;
    	String selectSql = "select * from StudentForm";
    	
        try {
        	con = new StudentDB().connectDataBase();
        	Statement statement = con.createStatement();
        	ResultSet rs = statement.executeQuery(selectSql);
        	
        	while(rs.next()) {
        		String studentID = rs.getString("studentID");
        		String name = rs.getString("name");
        		String major = rs.getString("major");
        		int age = rs.getInt("age");
        		String gender = rs.getString("gender");
        		studentForm.put(studentID, new ImmutableStudent(name, major, age, gender));
        	}

        	rs.close();
        	con.close();
        } catch(SQLException e) {
        	e.printStackTrace();
        }
        
	}
	
	// 根据学号获得学生信息
	public ImmutableStudent getStudent(String studentID) {
		return studentForm.get(studentID);
	}
	
	private static HashMap<String, ImmutableStudent> Copy(HashMap<String, ImmutableStudent> oldMap) {
		HashMap<String, ImmutableStudent> newMap = new HashMap<String, ImmutableStudent>();
		
		for(String Key : oldMap.keySet()) {
			newMap.put(Key, new ImmutableStudent(oldMap.get(Key)));
		}
		
		return newMap;
	}
	
	public static void updateSystem(ManagementSystem newManagementSystem) {
		managementSystem = newManagementSystem;
	}
	
	//JDK提供的创建不可变对象集合,不过它不是真正意义上的不可变对象集合
	public HashMap<String, ImmutableStudent> getImmutableMap() {
		return (HashMap<String, ImmutableStudent>) Collections.unmodifiableMap(Copy(studentForm));
	}
	
}
