package com.transfar.hr.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.transfar.hr.config.Config;

public class OracleJDBC {
	static{
		 try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	static private Connection hrConn = null;
	static public Connection getHRConnection() throws ClassNotFoundException, SQLException{
		if(hrConn==null){
			 Config cfg = Config.getInstance();
			 Properties properties = cfg.getProperties();
			 String url = "jdbc:oracle:thin:@"+properties.getProperty("oracle.service");//"thin:@127.0.0.1:1521:XE";//10.100.2.51:1528/HRTST
			 String user = properties.getProperty("oracle.user");
			 String passwd = properties.getProperty("oracle.password");
			 hrConn = DriverManager.getConnection(url, user, passwd);
		}
		return hrConn;
	}
	
	

	/**
	 *  drop table in oracle DB
	 * @param conn
	 * @param table
	 * @return
	 */
	static public boolean dropTable(Connection conn, String table){
		StringBuilder sql =new StringBuilder("begin execute immediate 'drop table ");
		sql.append(table);
		sql.append("'; exception when others then null;end;");
		//System.out.println(sql.toString());
		Statement stmt = null;
		try {
			stmt = conn.createStatement();//(sql.toString());
			stmt.executeUpdate(sql.toString());
			stmt.close();			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
