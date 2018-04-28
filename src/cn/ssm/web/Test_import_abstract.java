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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.ssm.entity.AbstractDetail;
import cn.ssm.entity.ComparisonAbstract;
import cn.ssm.utils.GetKemuList;
import cn.ssm.utils.JdbcUtil;

public class Test_import_abstract {
	public static Connection conn = null;
	public static PreparedStatement ps = null;
	public static ResultSet rs = null;

	public static void main(String[] args) {

		FileInputStream fis = null;
		try {
			fis = new FileInputStream("D:/工作簿1.xlsx");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			/*
			 * HSSFWorkbook hwk = new HSSFWorkbook(fis); HSSFSheet sheet =
			 * hwk.getSheetAt(0);
			 */

			Sheet sheet = null;
			XSSFWorkbook xb = new XSSFWorkbook(fis);
			sheet = xb.getSheetAt(0);

			int firstRowNum = sheet.getFirstRowNum();
			int lastRowNum = sheet.getLastRowNum();
			Row row = null;
			Cell cell_a = null;// 摘要
			Cell cell_b = null;// 编号
			Cell cell_c = null;// 借方
			Cell cell_d = null;// 凭证编码
			// Cell cell_e = null;// 贷方
			try {

			} catch (Exception e1) {

				e1.printStackTrace();
			}

			for (int j = firstRowNum; j < lastRowNum; j++) {

				/*
				 * String noName = null; String[] noNameAccount =
				 * {"凭证编号","业务说明","科目编号","借方发生额","贷方发生额"}; String[]
				 * noNameAbstract = {"上级科目名称","科目编号","科目名称","科目类型","借贷方向"}; if
				 * (j==firstRowNum) { for (int i = 0; i < lastRowNum; i++) {
				 * 
				 * } }
				 */

				if (j < 1) {
					continue;
				}
				try {
					row = sheet.getRow(j); // 取得第i行
					cell_d = row.getCell(4);// 凭证编码
					cell_a = row.getCell(7); // 摘要
					cell_b = row.getCell(8); // 科目编码
					cell_c = row.getCell(10);// 借方
					// cell_e = row.getCell(11);// 贷方
					ComparisonAbstract ca = new ComparisonAbstract();// 存去重后的摘要对象

					String vAbstract = cell_a.getStringCellValue().trim();// 取excle摘要

					String vCode = cell_b.getStringCellValue().trim();// 取excle科目编码

					// String debit = cell_c.getStringCellValue().trim();//
					// 取excle借方

					double debit = cell_c.getNumericCellValue();// 取excle借方

					String pCode = cell_d.getStringCellValue().trim();// 取excle凭证编码

					// String credit =
					// cell_e.getStringCellValue().trim();//取excle贷方

					/*
					 * try { if ((!debit.equals("0.00")) && debit != null) {
					 * 
					 * ca.setDebitAccount(vCode);
					 * 
					 * } else { ca.setCreditAccount(vCode);
					 * 
					 * }
					 */

					try {
						if (debit > 0) {

							ca.setDebitAccount(vCode);

						} else {
							ca.setCreditAccount(vCode);

						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (ca.getDebitAccount() == null) {

						ca.setDebitAccount("");
					} else if (ca.getCreditAccount() == null) {
						ca.setCreditAccount("");
					}
					ca.setVoucherAbstract(vAbstract);

					int i = GetKemuList.getRepeat(vAbstract, pCode, ca.getDebitAccount(), ca.getCreditAccount());// 去重

					if (i >= 1) {
						continue;
					} else {
						ca.setDebitAccount(ca.getDebitAccount());
						ca.setCreditAccount(ca.getCreditAccount());
						ca.setVoucherAbstract(vAbstract);
						ca.setVoucherCode(pCode);

						AbstractDetail ad = new AbstractDetail();

						ca.setVoucherCode(pCode);// 设置凭证编码-摘要表

						ad.setVoucherCode(pCode);// 设置凭证编码-凭证表
						ad.setCompanyName("北京赛科世纪科技股份有限公司");// 设置公司名称

						int detail = insertDetail(ad);
						if (detail != 0) {
							System.out.println("添加凭证表成功");
						} else {
							System.out.println("添加凭证表失败");
						}

						int insert = insertAbstract(ca);
						if (insert == 0) {
							System.out.println("添加摘要失败");
						} else {
							System.out.println("添加摘要成功");
						}
						System.out.println(ca.toString());
						System.out.println(ad.toString());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 添加凭证摘要表对象
	 * 
	 * @param ca
	 * @return
	 * @throws IOException
	 */
	public static int insertAbstract(ComparisonAbstract ca) throws IOException {

		int i = 0;
		String sql = "insert into comparison_abstract(voucher_code,voucher_abstract,debit_account,credit_account)values('"
				+ ca.getVoucherCode() + "','" + ca.getVoucherAbstract() + "','" + "" + ca.getDebitAccount() + "','"
				+ ca.getCreditAccount() + "')";
		try {
			ps = JdbcUtil.getConn().prepareStatement(sql);
			i = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeAll(rs, ps, conn);
		}
		return i;
	}

	/**
	 * 添加凭证编码对象
	 * 
	 * @param ca
	 * @return
	 * @throws IOException
	 */
	public static int insertDetail(AbstractDetail ad) throws IOException {

		int i = 0;
		String sql = "insert into abstract_detail(voucher_code,company_name)values('" + ad.getVoucherCode() + "','"
				+ ad.getCompanyName() + "')";
		try {
			ps = JdbcUtil.getConn().prepareStatement(sql);
			i = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeAll(rs, ps, conn);
		}
		return i;
	}

	public static ResultSet queryAll() throws SQLException, IOException {
		String sql = "select * from comparison_abstract";
		ps = JdbcUtil.getConn().prepareStatement(sql);
		rs = ps.executeQuery();
		return rs;
	}
}
