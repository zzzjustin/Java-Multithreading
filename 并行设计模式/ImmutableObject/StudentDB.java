package immutableobject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentDB {
	//连接操作
		public Connection connectDataBase() {
			//首先实例化一个数据库连接对象
			Connection con = null;
			//驱动程序名
	        String driver = "com.mysql.jdbc.Driver";
	        
	        //databaseName指向要访问的数据库名
	        String databaseName = "jdbc:mysql://localhost:3306/blackpink?useSSL=false";
	        //MySQL配置时的用户名
	        String user = "root";
	        //MySQL配置时的密码
	        String password = "94979527";
	        
	        //开始数据库连接操作
	        try {
	        	//加载驱动程序
	            Class.forName(driver);
	            //数据库连接对象con调用驱动管理的getConnection()方法实现连接
	            con = DriverManager.getConnection(databaseName,user,password);
	            //判断连接是否成功
	            if(!con.isClosed()) {
	            	System.out.println("数据库连接成功!");
	            }
	        } catch(ClassNotFoundException e) {
	        	//数据库驱动类异常处理
	        	e.printStackTrace();
	        } catch(SQLException e) {
	        	e.printStackTrace();
	        }
	        //最后返回数据库连接对象
	        return con;
		}
		
		//数据库查询操作
		public void selectDataBase() {
			//还是先声明Connection对象
	        Connection con = null;
	        //查询语句
	    	String selectSql = "select * from BLACKPINK";
	    	ResultSet rs2;
	    	
	        try {
	        	//先连接数据库
	        	con = connectDataBase();
	        	//实例化statement类对象，用来执行SQL语句
	        	Statement statement = con.createStatement();
	        	//实例化ResultSet类，用来存放获取的结果集
	        	ResultSet rs = statement.executeQuery(selectSql);
	        	rs2 = rs;
	        	
	        	String name;
	        	String job;
	        	//从查询结果集中获取信息
	        	while(rs.next()) {
	        		name = rs.getString("name");
	        		job = rs.getString("job");
	        		System.out.println("name:"+name+" job:"+job);
	        	}
	        	
	        	//操作完毕后,关闭查询结果集和数据库连接
	        	rs.close();
	        	con.close();
	        } catch(SQLException e) {
	        	//数据库连接失败异常处理
	        	e.printStackTrace();
	        }
	        
		}
}
