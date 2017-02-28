package cn.notemi.db.util;

import java.sql.*;

public class Conn {
	static String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";

	static{
		try{
			Class.forName(driverName);
			System.out.println("Success loading Mysql Driver!");
		}catch(Exception e) {
			System.out.println("Error loading Mysql Driver!");
			e.printStackTrace();
		} 
	}
	
	public static Connection getConn(String url,String userName,String password){
		try {
			Connection conn = DriverManager.getConnection(url, userName, password);
			System.out.println("Success Connection Mysql!");
			return conn;
		} catch (SQLException e) {
			System.out.println("Error Connection Mysql!");
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		getConn("jdbc:sqlserver://127.0.0.1:1433;databaseName=yygl","sa","123456789");
	}
}
