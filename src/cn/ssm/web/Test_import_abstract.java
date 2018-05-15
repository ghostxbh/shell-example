package cn.ssm.web;

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
import cn.ssm.entity.AbstractDetail;
import cn.ssm.entity.ComparisonAbstract;
import cn.ssm.entity.ComparisonAccount;
import cn.ssm.utils.CellType;
import cn.ssm.utils.GetKemuList;
import cn.ssm.utils.JdbcUtil;

public class Test_import_abstract {
	public static Connection conn = null;
	public static PreparedStatement ps = null;
	public static ResultSet rs = null;

	/**
	 * 取得科目表中的原有的科目名称和编号
	 * 
	 * @return List<ComparisonAccount>
	 * @throws IOException
	 */
	public static List<ComparisonAccount> code() throws IOException {
		List<ComparisonAccount> list = new ArrayList<ComparisonAccount>();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream("D:/01.xls");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		HSSFWorkbook hwk = new HSSFWorkbook(fis);
		HSSFSheet sheet = hwk.getSheetAt(0);
		int firstRowNum = sheet.getFirstRowNum();
		int lastRowNum = sheet.getLastRowNum();

		Row row = null;
		String o = "/";
		for (int j = firstRowNum; j <= lastRowNum; j++) {
			if (j < 1) {
				continue;
			}
			row = sheet.getRow(j); // 取得第j行

			String lastName = CellType.getCellValue(row.getCell(1));// 上级

			String accountCode = CellType.getCellValue(row.getCell(2));// 科目编号

			String accountName = CellType.getCellValue(row.getCell(3));// 科目名称

			/**
			 * 判断上级为空的情况
			 */

			ComparisonAccount account = new ComparisonAccount();
			account.setAccountCode(accountCode);
			// 科目名称
			if (lastName != null && !lastName.equals("")) {

				// 判断首位是不是\
				if (lastName.startsWith("\\")) {
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
						account.setFullName(fullName);// 设置account全目录名称
					}
				}
			} else {
				account.setFullName(accountName);
			}
			account.setFullName(account.getFullName());
			list.add(account);
		}
		return list;
	}

	static String repeatCode;

	/**
	 * 根据科目表对应的编码找出全路径名称，并对没有和不符合要求的凭证删除，返回一个以凭证号为键的Map集合
	 * 
	 * @return Map<String, List<ComparisonAbstract>>
	 * @throws IOException
	 */
	public static void main(String[] agrs) throws IOException {

		// Map<String, List<ComparisonAbstract>> map = new HashMap<String,
		// List<ComparisonAbstract>>();

		// List<ComparisonAbstract> list = new ArrayList<ComparisonAbstract>();

		List<ComparisonAccount> scaList;

		FileInputStream fis = null;
		try {
			fis = new FileInputStream("D:/02.xls");

			HSSFWorkbook hwk = new HSSFWorkbook(fis);
			HSSFSheet sheet = hwk.getSheetAt(0);

			int firstRowNum = sheet.getFirstRowNum();
			int lastRowNum = sheet.getLastRowNum();
			Row row = null;

			String[] uselessCode = { "银行存款", "存放中央银行款项", "其他货币资金", "应收账款", "预付账款", "其他应收款", "原材料", "库存商品", "发出商品",
					"长期股权投资", "无形资产", "长期待摊费用", "固定资产", "累计折旧", "委托加工物资", "应收股利", "应付账款", "其他应付款", "主营业务成本", "应付股利",
					"长期借款", "实收资本", "其他综合收益", "主营业务收入", "专项应付款", "在建工程", "应收票据", "短期借款", "应付票据", "预收账款" ,"投资收益"};

			for (int j = firstRowNum; j <= lastRowNum; j++) {

				if (j < 1) {
					continue;
				}

				row = sheet.getRow(j); // 取得第i行
				// 凭证编码
				String voucherCode = CellType.getCellValue(row.getCell(4));
				// 摘要
				String voucherAbstract = CellType.getCellValue(row.getCell(7));
				// 科目编码
				String accountCode = CellType.getCellValue(row.getCell(8));
				// 借方
				String debitAccount = CellType.getCellValue(row.getCell(10));
				// 借方
				String creditAccount = CellType.getCellValue(row.getCell(11));

				ComparisonAbstract ca = new ComparisonAbstract();
				// 摘要
				ca.setVoucherAbstract(voucherAbstract);
				// 凭证
				ca.setVoucherCode(voucherCode);
				// 判断是否为空的摘要信息,和不用的摘要信息,摘要为空同级的凭证也跳出
				String[] no = { "结转期间损益", "期间损益结转" };
				if (voucherAbstract == null || voucherAbstract.equals("")) {
					continue;
				} else if (Arrays.asList(no).contains(voucherAbstract)) {
					continue;
				}
				// 排除凭证号中的一些无用数据
				if (voucherCode.length() > 2) {
					int parseInt = Integer.parseInt(voucherCode);
					ca.setVoucherCode(String.valueOf(parseInt));
				}

				scaList = code();
				String replaceCode = null;
				for (ComparisonAccount cab : scaList) {
					String aCode = cab.getAccountCode();
					if (aCode.equals(accountCode)) {
						String fullName = cab.getFullName();
						if (fullName.contains("营业税金及附加")) {
							fullName = fullName.replace("营业税金及附加", "税金及附加");
						}
						String[] split = fullName.split("/");
						if (Arrays.asList(uselessCode).contains(split[0])) {
							replaceCode = GetKemuList.getfName(split[0]);
						} else {
							replaceCode = GetKemuList.getfName(fullName);
						}
						break;
					} else {
						continue;
					}
				}
				try {
					if (debitAccount.contains(".")) {
						String replaceAll = debitAccount.replaceAll("[,]", "");
						double parseDouble = Double.parseDouble(replaceAll);
						if (parseDouble < 0 | parseDouble > 0) {
							ca.setDebitAccount(replaceCode);
						} else {
							ca.setCreditAccount(replaceCode);
						}
					} else if (creditAccount.contains(".")) {
						String replaceAll = creditAccount.replaceAll("[,]", "");
						double parseDouble = Double.parseDouble(replaceAll);
						if (parseDouble < 0 | parseDouble > 0) {
							ca.setCreditAccount(replaceCode);
						} else {
							ca.setDebitAccount(replaceCode);
						}
					} else if (debitAccount == null | debitAccount.equals("")) {
						ca.setCreditAccount(replaceCode);
					} else if (debitAccount == null | debitAccount.equals("")) {
						ca.setDebitAccount(replaceCode);
					} else {
						String replaceAll = debitAccount.replaceAll("[,]", "");
						int parseInt = Integer.parseInt(replaceAll);
						if (parseInt < 0 | parseInt > 0) {
							ca.setDebitAccount(replaceCode);
						} else {
							ca.setCreditAccount(replaceCode);
						}
					}

				} catch (Exception e) {
					System.out.println("————————————————————————————");
				}

				if (ca.getDebitAccount() == null) {
					ca.setDebitAccount("");
				} else if (ca.getCreditAccount() == null) {
					ca.setCreditAccount("");
				}

				// 获取数据库中凭证编码最大值
				int maxCode = GetKemuList.getVoucherCode();
				// 上一行凭证编码
				String lastCode = CellType.getCellValue(sheet.getRow(j - 1).getCell(4));

				if (ca.getVoucherCode().equals(lastCode)) {
					ca.setVoucherCode(String.valueOf(maxCode));
				} else {
					ca.setVoucherCode(String.valueOf(maxCode + 1));
				}
				/*
				 * if (ca.getCreditAccount() == null || ca.getDebitAccount() ==
				 * null || ca.getVoucherAbstract() == null) { replaceCode =
				 * ca.getVoucherCode(); if (!voucherCode.equals(lastCode)) { int
				 * d = GetKemuList.delectAbstractByCode(replaceCode); if (d > 0)
				 * { System.out.println("删除含null的凭证号"); } else {
				 * System.out.println("删除失败"); } } } else {
				 */

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
		int maxCode = GetKemuList.getMaxCode();
		AbstractDetail ad = new AbstractDetail();
		for (int k = maxCode + 1; k <= maxvCode; k++) {
			maxCode = GetKemuList.getMaxCode();
			ad.setCompanyName("浙江车马炮物联网络有限公司");// 设置公司名称
			ad.setVoucherCode(String.valueOf(k));
			ad.setPriority("2");
			try {
				if (GetKemuList.getVoucherCode(ad.getVoucherCode()) < 1) {
					ad.setVoucherCode(String.valueOf(k));
				} else {
					continue;  
				}    
			} catch (Exception e) {

				e.printStackTrace();
			}
			int detail = Test_import_abstract.insertDetail(ad);
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
		String sql = "insert into comparison_abstract_detail(voucher_code,company_name,priority)values('"
				+ ad.getVoucherCode() + "','" + ad.getCompanyName() + "','" + ad.getPriority() + "')";
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
