package cn.ssm.web;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.ssm.entity.ComparisonAccount;
import cn.ssm.utils.GetKemuList;
import cn.ssm.utils.JdbcUtil;

public class Test_import_initial {
	public static Connection conn = null;
	public static PreparedStatement ps = null;
	public static ResultSet rs = null;

	public static boolean isExcel2003(String filePath) {
		return filePath.matches("^.+\\.(?i)(xls)$");
	}

	// @描述：是否是2007的excel，返回true是2007
	public static boolean isExcel2007(String filePath) {
		return filePath.matches("^.+\\.(?i)(xlsx)$");
	}

	public static Workbook getWorkbook() throws IOException {
		FileInputStream fis = null;
		Workbook wb = null;
		try {
			String name = "D:/真账/账目公司/1、北京赛科世纪科技股份有限公司/2016年度/7、科目余额表/科目余额表.xls";
			fis = new FileInputStream(name);
			/*
			 * BufferedReader tBufferedReader = new BufferedReader(new
			 * InputStreamReader(fis, "UTF-8")); StringBuffer tStringBuffer =
			 * new StringBuffer(); String sTempOneLine = new String(""); while
			 * ((sTempOneLine = tBufferedReader.readLine()) != null) {
			 * tStringBuffer.append(sTempOneLine); }
			 */
			if (isExcel2003(name)) {// 当excel是2003时,创建excel2003
				wb = new HSSFWorkbook(fis);
				return wb;
			} else {// 当excel是2007时,创建excel2007
				wb = new XSSFWorkbook(fis);
				return wb;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return wb;

	}

	public static void main(String[] args) throws Exception {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream("D:/16年1-9科目余额表.XLS");
			
			HSSFWorkbook hwk = new HSSFWorkbook(fis);
			HSSFSheet sheet = hwk.getSheetAt(0);
			
			/*Sheet sheet = null;			
			XSSFWorkbook xb = new XSSFWorkbook(fis);
			sheet = xb.getSheetAt(0);*/

			int firstRowNum = sheet.getFirstRowNum();
			int lastRowNum = sheet.getLastRowNum();

			Row row = null;
			Cell cell_a = null;// 上级
			Cell cell_b = null;// 科目名称
			Cell cell_c = null;// 类型
			Cell cell_d = null;// 方向
			Cell cell_e = null;// 编号
			String o = "/";
			for (int j = firstRowNum; j < lastRowNum; j++) {
				if (j < 1) {
					continue;
				}
				try {
					row = sheet.getRow(j); // 取得第i行

					cell_a = row.getCell(1);// 取编号列

					cell_e = row.getCell(2);

					cell_b = row.getCell(3);

					cell_c = row.getCell(4);

					cell_d = row.getCell(5);
					/**
					 * 判断上级为空的情况
					 */
					if (cell_a != null) {
						ComparisonAccount account = new ComparisonAccount();

						String accountCode = cell_e.getStringCellValue().trim();
						account.setAccountCode(GetKemuList.getAccountCode(accountCode));// 设置account编码

						account.setAccountName(cell_b.getStringCellValue().trim());// 设置account名称

						String cellValue1 = cell_b.getStringCellValue().trim();// 获取科目名称
						String cellValue2 = cell_a.getStringCellValue().trim();// 获取上级科目名称
						String substring = cellValue2.substring(1);
						String replaceAll = substring.replace("\\", "/");// 替换反斜杠
						String cellValue3 = replaceAll + o + cellValue1;// 全目录名称

						account.setFullName(cellValue3);// 设置account全目录名称

						account.setAccountParentId(GetKemuList.getParentId(account.getAccountCode(),
								account.getFullName(), account.getAccountName()));// 设置account上级id

						// 判断借贷方向
						String dc = "";
						if (cell_d.getStringCellValue().toString().equals("借")) {
							dc = "0";
						} else {
							dc = "1";
						}
						account.setAccountDirect(dc);// 设置借贷编号

						/**
						 * 判断类型
						 */

						int kemu1 = 0;
						String type = cell_c.getStringCellValue().toString();
						if (type.contains("资产")) {
							kemu1 = 1;
						} else if (type.contains("负债")) {
							kemu1 = 2;
						} else if (type.contains("共同")) {
							kemu1 = 3;
						} else if (type.contains("权益")) {
							kemu1 = 4;
						} else if (type.contains("成本")) {
							kemu1 = 5;
						} else if (type.contains("损益")) {
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

					}

				}

				catch (Exception e) {
					e.printStackTrace();
				}

			}

		} catch (

		IOException e) {
			e.printStackTrace();
		} finally {

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
			ps = JdbcUtil.getConn().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			i = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeAll(rs, ps, conn);
		}
		return i;
	}

}