package Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;

import cn.ssm.entity.AbstractDetail;
import cn.ssm.entity.ComparisonAbstract;
import cn.ssm.entity.ComparisonAccount;
import cn.ssm.utils.CellType;
import cn.ssm.utils.GetKemuList;
import cn.ssm.utils.JdbcUtil;

public class Import {

	public static Connection conn = null;
	public static PreparedStatement ps = null;
	public static ResultSet rs = null;


	public static void main(String[] agrs) throws IOException {

		FileInputStream fis = null;
		try {
			fis = new FileInputStream("D:/整理后常用分录.xls");

			HSSFWorkbook hwk = new HSSFWorkbook(fis);
			HSSFSheet sheet = hwk.getSheetAt(0);

			int firstRowNum = sheet.getFirstRowNum();
			int lastRowNum = sheet.getLastRowNum();
			Row row = null;
			String o = "/";
			List<ComparisonAbstract> list = new ArrayList<ComparisonAbstract>();
			for (int j = firstRowNum; j <= lastRowNum; j++) {
				if (j < 1) {
					continue;
				}
				row = sheet.getRow(j); // 取得第i行
				// 凭证编码
				String voucherCode = CellType.getCellValue(row.getCell(0));
				// 摘要
				String voucherAbstract = CellType.getCellValue(row.getCell(1));
				// 借贷方向
				String cd = CellType.getCellValue(row.getCell(2));
				// 一级科目
				String one = CellType.getCellValue(row.getCell(3));
				// 二级科目
				String two = CellType.getCellValue(row.getCell(4));
				// 三级科目
				String three = CellType.getCellValue(row.getCell(5));
				
				
				ComparisonAccount cc = new ComparisonAccount();

				ComparisonAbstract ca = new ComparisonAbstract();
				// 摘要
				ca.setVoucherAbstract(voucherAbstract);
				// 凭证
				ca.setVoucherCode(voucherCode);
				String fullName = null;
				if (two != null && !two.equals("")) {
					fullName = one + o + two;
				} else if (three != null && !three.equals("")) {
					fullName = one + o + two + o + three;
				} else {
					fullName = one;
				}
					
				String code = GetKemuList.getbName(fullName);
				if (code == null & fullName.contains("/")) {
					String[] split = fullName.split("/");
					ComparisonAccount cac = GetKemuList.getFirstAccount(split[0]);
					if (split.length < 3) {
						int maxCode = GetKemuList.getMaxCode(cac.getId());
						cc.setAccountCode(String.valueOf(maxCode + 1));
						cc.setAccountName(split[1]);
						cc.setFullName(fullName);
						cc.setAccountType(cac.getAccountType());
						cc.setAccountDirect(cac.getAccountDirect());
						cc.setAccountParentId(cac.getId());
						int insert = insert(cc);
						if (insert > 0) {
							System.out.println("添加二级（最大）成功");
							System.out.println(cc.toString());
						} else {
							System.out.println("添加失败");
						}
					} else {
						String substring = fullName.substring(0, fullName.lastIndexOf("/"));
						String getfName = GetKemuList.getbName(substring);
						int maxCode = GetKemuList.getMaxCode(GetKemuList.getId(GetKemuList.getfName(split[0])));
						int second = maxCode + 1;
						if (getfName == null) {
							cc.setAccountCode(String.valueOf(second));
							cc.setAccountName(split[split.length - 1]);
							cc.setFullName(substring);
							cc.setAccountType(cac.getAccountType());
							cc.setAccountDirect(cac.getAccountDirect());
							cc.setAccountParentId(cac.getId());
							int insert = insert(cc);
							if (insert > 0) {
								System.out.println("添加二级科目成功");
								System.out.println(cc.toString());
							} else {
								System.out.println("添加失败");
							}

						}
						cc.setAccountCode(String.valueOf(second) + "0001");
						cc.setAccountName(split[split.length]);
						cc.setFullName(fullName);
						cc.setAccountType(cac.getAccountType());
						cc.setAccountDirect(cac.getAccountDirect());
						cc.setAccountParentId(GetKemuList.getParentId(substring));
						int insert = insert(cc);
						if (insert > 0) {
							System.out.println("添加三级科目成功");
							System.out.println(cc.toString());
						} else {
							System.out.println("添加失败");
						}

					}

				}
				code = GetKemuList.getbName(fullName);

				if (cd.equals("借")) {
					ca.setDebitAccount(code);
				} else {
					ca.setCreditAccount(code);
				}
				if (ca.getDebitAccount() == null) {
					ca.setDebitAccount("");
				} else if (ca.getCreditAccount() == null) {
					ca.setCreditAccount("");
				}

				int insert = insertAbstract(ca);
				if (insert > 0) {
					System.out.println("添加成功");
					System.out.println(ca.toString());
				} else {
					System.out.println("添加失败");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * 添加凭证表数据
		 */
		Integer maxvCode = GetKemuList.getVoucherCode();
		AbstractDetail ad = new AbstractDetail();
		for (int k = 1; k <= maxvCode; k++) {
			ad.setCompanyName("北京云账房信息有限公司");// 设置公司名称
			ad.setPriority("1");
			ad.setVoucherCode(String.valueOf(k));
			try {
				if (GetKemuList.getVoucherCode(ad.getVoucherCode()) < 1) {
					ad.setVoucherCode(String.valueOf(k));
				} else {
					continue;
				}
			} catch (Exception e) {

				e.printStackTrace();
			}
			int detail = insertDetail(ad);
			if (detail != 0) {
				System.out.println("添加凭证表成功");
			} else {
				System.out.println("添加凭证表失败");
			}
			System.out.println(ad.toString());
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
		String sql = "insert into abstract_detail(voucher_code,company_name,priority)values('" + ad.getVoucherCode()
				+ "','" + ad.getCompanyName() + "','" + ad.getPriority() + "')";
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

	public static ResultSet queryAll() throws SQLException, IOException {
		String sql = "select * from comparison_abstract";
		ps = JdbcUtil.getConn().prepareStatement(sql);
		rs = ps.executeQuery();
		return rs;
	}
}
