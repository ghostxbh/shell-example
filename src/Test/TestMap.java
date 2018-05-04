package Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

	public static void main(String[] args) throws FileNotFoundException, ParseException {

		// StringBuffer sb = new StringBuffer("10020105");
		// StringBuffer insert = sb.insert(4, new char[]{'0','0'});
		// StringBuffer insert2 = insert.insert(8, new char[]{'0','0'});
		// String a = new String(insert2);
		// System.out.println(a);
		// 判断首位是不是\符号
		String accountCode1 = "4001.002";
		/*
		 * String o = "/"; if ("\\那嘎是国际奥赛\\gd\\adsasg "
		 * .substring(0,1).equals("\\")) { String substring =
		 * "\\那嘎是国际奥赛\\gd\\adsasg".substring(1);
		 * 
		 * String cellValue3 = substring + o + cellValue1;// 全目录名称 String
		 * replaceAll = cellValue3.replace("\\", "/");// 替换反斜杠
		 * System.out.println(replaceAll); }
		 */
		/*
		 * if (accountCode.contains(".")) { String replaceCode =
		 * accountCode.replace(".",""); System.out.println(replaceCode); }
		 */
		String l = "\\4001008006";

		/*
		 * if (l.startsWith("\\")) { String substring = l.substring(1);
		 * System.out.println(substring); }
		 */

		// String[] split={"商品","豆角","大豆","豆芽","玉米"};
		// System.out.println(split[split.length-2]);
		/*
		 * String ll = "\\在线会计\\商品上架\\货款"; String gg = "瓜子"; String o = "/"; if
		 * (ll.startsWith("\\")) {// 判断首位是不是\ String substring =
		 * ll.substring(1); if (substring.contains("\\")) { String replace =
		 * substring.replace("\\", "/"); String fullName = replace + o + gg;//
		 * 全目录名称 System.out.println(fullName); } else {
		 * System.out.println(substring+o+gg); }
		 * 
		 * } else { String fullName = ll + o + gg;// 全目录名称 if
		 * (fullName.contains("\\")) { String replaceAll = fullName.replaceAll(
		 * "\\", "/"); System.out.println(replaceAll); } else {
		 * System.out.println(fullName); }
		 * 
		 * }
		 */
		/*
		 * String accountCode = "400101055"; if (accountCode.substring(4,
		 * 5).equals("0")&&accountCode.substring(6, 7).equals("0")){
		 * StringBuffer sb = new StringBuffer(accountCode); StringBuffer insert1
		 * = sb.insert(4, new char[] { '0', '0' }); StringBuffer insert2 =
		 * insert1.insert(8, '0' ); String newAccountCode = new String(insert2);
		 * System.out.println(newAccountCode); }
		 */
	/*	StringBuffer sb = new StringBuffer("20161103");
		StringBuffer insert2 = sb.insert(4, "-");
		StringBuffer insert3 = insert2.insert(7, "-");
		String sdate = new String(insert3);
		Date parse = new SimpleDateFormat().parse(sdate);
		System.out.println(parse);*/
		double debit = -15454.23;
		String replaceAll = String.valueOf(debit).replaceAll("[p{P}+~$`^=|<>～｀＄＾＋＝｜—＜＞（）-￥×]", "");
		System.out.println(replaceAll);
	}
}
