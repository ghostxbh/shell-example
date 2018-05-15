package Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

import cn.ssm.utils.GetKemuList;

public class TestMap {
	public static Connection conn = null;
	public static PreparedStatement ps = null;
	public static ResultSet rs = null;

	public static void main(String[] args) {
		/**
		 * 删除带null凭证
		 */
		/*List<String> codeList = GetKemuList.getCodeList();
		for (String string : codeList) {
			System.out.println(string);

			int de = GetKemuList.delectAbstractByCode(string);
			if (de > 0) {
				System.out.println("删除含null的摘要表成功");
			} else {
				System.out.println("————————摘要表————失败————————————");
			}
			int db = GetKemuList.delectDetailByCode(string);
			if (db > 0) {
				System.out.println("删除含null的凭证表成功");
			} else {
				System.out.println("————————凭证表————失败————————————");
			}
		}*/
		/**
		 * 导入标准库其他所有凭证加1000
		 */
		/*List<String> codeList = GetKemuList.getCodeList1();
		for (String string : codeList) {
			System.out.println(string);
			int i = Integer.parseInt(string)+1000;
			int de = GetKemuList.updateCode(String.valueOf(i));
			if (de > 0) {
				System.out.println("成功");
			} else {
				System.out.println("————————————失败————————————");
			}
			
		}*/
		/**
		 * 删除借贷方向只有一个的凭证
		 */
		List<String> list = GetKemuList.getCodeListByCode();
		for (String string : list) {
			System.out.println(string);

			int de = GetKemuList.delectAbstractByCode(string);
			if (de > 0) {
				System.out.println("删除摘要表成功");
			} else {
				System.out.println("————————摘要表————失败————————————");
			}
			int db = GetKemuList.delectDetailByCode(string);
			if (db > 0) {
				System.out.println("删除凭证表成功");
			} else {
				System.out.println("————————凭证表————失败————————————");
			}
		}
		
	}

}
