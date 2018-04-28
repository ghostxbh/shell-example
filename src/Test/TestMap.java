package Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

public class TestMap {
	private static JdbcTemplate template = null;

	/*
	 * public static List<Map<String, Object>> queryAccountId() throws
	 * SQLException { String sql = "select * from comparison_account";
	 * List<Map<String, Object>> forList = template.queryForList(sql); return
	 * forList; }
	 */

	public static void main(String[] args) throws FileNotFoundException {
		FileInputStream fis = new FileInputStream("D:/真账/账目公司/1、北京赛科世纪科技股份有限公司/2016年度/7、科目余额表/科目余额表.xls");
		System.out.println(fis);
	}	
}
