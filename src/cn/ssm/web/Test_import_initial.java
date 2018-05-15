package cn.ssm.web;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import cn.ssm.entity.ComparisonAccount;
import cn.ssm.utils.CellType;
import cn.ssm.utils.GetKemuList;
import cn.ssm.utils.JdbcUtil;

public class Test_import_initial {
	public static Connection conn;
	public static PreparedStatement ps;
	public static ResultSet rs;

	public static void main(String[] args) throws Exception {
		List<ComparisonAccount> list = new ArrayList<ComparisonAccount>();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream("D:/01.xls");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		HSSFWorkbook hwk = new HSSFWorkbook(fis);
		HSSFSheet sheet = hwk.getSheetAt(0);

		/*
		 * Sheet sheet = null; XSSFWorkbook xb = new XSSFWorkbook(fis); sheet =
		 * xb.getSheetAt(0);
		 */

		int firstRowNum = sheet.getFirstRowNum();
		int lastRowNum = sheet.getLastRowNum();

		Row row = null;
		String o = "/";

		for (int j = firstRowNum; j < lastRowNum; j++) {
			if (j < 1) {
				continue;
			}

			row = sheet.getRow(j); // 取得第j行

			// 上级
			String lastName = CellType.getCellValue(row.getCell(1));
			// 科目名称
			String accountName = CellType.getCellValue(row.getCell(3));
			// 科目类型
			String accountType = CellType.getCellValue(row.getCell(4));
			// 借贷方向
			String accountDirect = CellType.getCellValue(row.getCell(5));
			/**
			 * 判断上级为空的情况
			 */
			try {
				if (lastName != null && !lastName.equals("")) {
					ComparisonAccount account = new ComparisonAccount();
					// 科目名称
					account.setAccountName(accountName);

					// 判断首位是不是\
					if (lastName.startsWith("\\")) { // 上级科目名称
						String substring = lastName.substring(1);
						// 判断是否包含\符号，替换成统一的/
						if (substring.contains("\\")) {
							String replaceAll = substring.replace("\\", "/");
							String fullName = replaceAll + o + accountName;// 全目录名称
							account.setFullName(fullName);
						} else {
							account.setFullName(substring + o + accountName);
						}

					} else {
						String fullName = lastName + o + accountName;// 全目录名称
						if (fullName.contains("\\")) {
							String replaceAll = fullName.replace("\\", "/");
							account.setFullName(replaceAll);
						} else {
							account.setFullName(fullName); // 设置account全目录名称
						}
					}
					/**
					 * 判断是否有重复的全路径名称，重复直接跳过
					 */
					if (GetKemuList.getFull(account.getFullName())) {
						continue;
					}

					// 设置编码，为空直接跳出
					account.setAccountCode(GetKemuList.getvCode(account.getFullName()));
					if (account.getAccountCode() == null) {
						continue;
					}

					// 设置account上级id，从数据库中匹配,返回为0直接跳过
					account.setAccountParentId(GetKemuList.getParentId(account.getFullName()));
					if (account.getAccountParentId() == 0) {
						continue;
					}

					// 判断借贷方向
					String dc = "";
					if (accountDirect.equals("借")) {
						dc = "0";
					} else {
						dc = "1";
					}
					account.setAccountDirect(dc);// 设置借贷编号

					/**
					 * 判断类型
					 */

					int kemu1 = 0;
					if (accountType.contains("资产")) {
						kemu1 = 1;
					} else if (accountType.contains("负债")) {
						kemu1 = 2;
					} else if (accountType.contains("共同")) {
						kemu1 = 3;
					} else if (accountType.contains("权益")) {
						kemu1 = 4;
					} else if (accountType.contains("成本")) {
						kemu1 = 5;
					} else if (accountType.contains("损益")) {
						kemu1 = 6;
					} else {
						System.out.println("没有获取到科目类型");
					}
					account.setAccountType(String.valueOf(kemu1));// 设置类型编号

					System.out.println(account.toString());

					int insert = insert(account);
					if (insert == 0) {
						System.out.println("添加失败");
					} else {
						System.out.println("添加成功");
					}
					list.add(account);
				} else {
					continue;// 上级科目为空的情况直接跳过
				}
			} catch (Exception e) {

				e.printStackTrace();
			}

		}

	}

	/**
	 * 添加account对象
	 * 
	 * @param account
	 * @return
	 * @throws IOException
	 */
	public static int insert(ComparisonAccount account) throws IOException {

		int i = 0;
		String sql = "insert into comparison_account(account_code,account_name,full_name,"
				+ "account_type,account_direct,account_parentId)" + "values('" + account.getAccountCode() + "','"
				+ account.getAccountName() + "'" + ",'" + account.getFullName() + "','" + account.getAccountType()
				+ "'," + "'" + account.getAccountDirect() + "','" + account.getAccountParentId() + "')";
		try {
			i = JdbcUtil.getConn().prepareStatement(sql).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeAll(rs, ps, conn);
		}
		return i;
	}

}