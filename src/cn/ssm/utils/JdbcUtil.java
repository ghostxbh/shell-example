package cn.ssm.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

public class JdbcUtil {
	// private static BasicDataSource dds;

	/*
	 * public static void init() { dds = new BasicDataSource();
	 * 
	 * try {
	 * 
	 * dds.setDriverClassName("com.mysql.jdbc.Driver"); dds.setUrl(
	 * "jdbc:mysql://127.0.0.1:3306/comparison?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false&maxReconnects=10"
	 * ); dds.setUsername("root"); dds.setPassword("123456");
	 * 
	 * // dds.setInitialSize(20); // dds.setMaxActive(10); //
	 * dds.setMaxWait(6000); // dds.setMaxIdle(20); // dds.setMinIdle(5); }
	 * catch (Exception e) { e.printStackTrace(); // throw new
	 * RuntimeException(e); } }
	 */

	/*
	 * public synchronized static Connection getConn() { if (dds == null) {
	 * init(); } try { return dds.getConnection(); } catch (SQLException e) {
	 * e.printStackTrace(); } return null; }
	 */
	public static Connection getConn() throws SQLException {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/comp?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false&maxReconnects=10";
		String username = "root";
		String password = "123456";
		try {
			Class.forName(driver);

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return DriverManager.getConnection(url, username, password);

	}

	public static void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) {

		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}
}
