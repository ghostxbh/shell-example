package cn.ssm.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.ssm.entity.AbstractDetail;
import cn.ssm.entity.ComparisonAccount;

public class GetKemuList {

	public static Connection conn = null;
	public static PreparedStatement ps = null;
	public static ResultSet rs = null;

	/**
	 * parentId从数据库中判断获取
	 * 
	 * @param accountCode
	 * @param fullName
	 * @param accountName
	 * @return
	 * @throws Exception
	 */
	public static Integer getParentId(String accountCode, String fullName) throws Exception {

		String sql = "select id,account_code,account_name from comparison_account";
		int i = 0;
		try {
			rs = JdbcUtil.getConn().prepareStatement(sql).executeQuery();
			/**
			 * 取出数据库的数据对比
			 */
			while (rs.next()) {
				ComparisonAccount account = new ComparisonAccount();
				account.setId(rs.getInt("id"));
				account.setAccountCode(rs.getString("account_code"));
				account.setAccountName(rs.getString("account_name"));

				String[] split = fullName.split("/");

				String code = accountCode.substring(0, 4);// 截取一级科目编码

				if (split[split.length - 2].equals(account.getAccountName())
						&& account.getAccountCode().contains(code)) {
					i = account.getId();

					return i;
				} else {

					continue;
				}

				/**
				 * 判断是否包含一级科目名字的字段
				 */

				/*
				 * if (code.equals(account.getAccountCode()) &&
				 * codeName.equals(account.getAccountName())) {
				 * 
				 * i = account.getId();
				 * 
				 * return i;
				 * 
				 * } else {
				 * 
				 * continue; }
				 */
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			JdbcUtil.closeAll(rs, ps, conn);
		}
		return i;

	}

	/**
	 * AccountCode重复判断
	 * 
	 * @param accountCode
	 * @return
	 * @throws Exception
	 */
	public static String getAccountCode(String accountCode, String fullName) {

		String sql = "select account_code,full_name from comparison_account";
		ComparisonAccount account = new ComparisonAccount();

		try {
			rs = JdbcUtil.getConn().prepareStatement(sql).executeQuery();

			while (rs.next()) {

				String sqlCode = rs.getString("account_code");
				String sqlFullName = rs.getNString("full_name");

				// 如果数据库中的accountCode与Excle表里面的Code相等,并且全目录名称相等，则直接跳过
				if (accountCode.equals(sqlCode) && fullName.equals(sqlFullName)) {
					return null;
				}

				// 分割全目录名称看分多少级
				String[] split = fullName.split("/");
				// 全目录级数
				int fullNamelength = split.length;
				// 去掉小数点的科目编码
				if (accountCode.contains(".")) {
					String replaceCode = accountCode.replace(".", "");
					account.setAccountCode(replaceCode);
				}
				// 判断如果是2级的和科目编码等于6位数，变成8位数
				if (fullNamelength == 2 && accountCode.length() == 6) {

					StringBuffer sb = new StringBuffer(accountCode);
					StringBuffer insert1 = sb.insert(4, new char[] { '0', '0' });
					String newAccountCode = new String(insert1);
					account.setAccountCode(newAccountCode);
					// 判断如果是2级的和科目编码等于7位数，变成8位数
				} else if (fullNamelength == 2 && accountCode.length() == 7) {

					StringBuffer sb = new StringBuffer(accountCode);
					StringBuffer insert1 = sb.insert(4, '0');
					String newAccountCode = new String(insert1);
					account.setAccountCode(newAccountCode);
					// 判断如果是3级的和科目编码等于8位数，变成12位数
				} else if (fullNamelength == 3 && accountCode.length() == 8) {
					StringBuffer sb = new StringBuffer(accountCode);
					StringBuffer insert1 = sb.insert(4, new char[] { '0', '0' });
					StringBuffer insert2 = insert1.insert(8, new char[] { '0', '0' });
					String newAccountCode = new String(insert2);
					account.setAccountCode(newAccountCode);
					// 判断如果是3级的和科目编码等于10位数，变成12位数
				} else if (fullNamelength == 3 && accountCode.length() == 10) {

					StringBuffer sb = new StringBuffer(accountCode);
					StringBuffer insert1 = sb.insert(4, '0');
					StringBuffer insert2 = insert1.insert(8, '0');
					String newAccountCode = new String(insert2);
					account.setAccountCode(newAccountCode);
					// 判断如果是4级的和科目编码等于10位数，变成16位数
				} else if (fullNamelength == 4 && accountCode.length() == 10) {
					StringBuffer sb = new StringBuffer(accountCode);
					StringBuffer insert1 = sb.insert(4, new char[] { '0', '0' });
					StringBuffer insert2 = insert1.insert(8, new char[] { '0', '0' });
					StringBuffer insert3 = insert2.insert(12, new char[] { '0', '0' });
					String newAccountCode = new String(insert3);
					account.setAccountCode(newAccountCode);
					// 判断如果是4级的和科目编码等于13位数，变成16位数
				} else if (fullNamelength == 4 && accountCode.length() == 13) {

					StringBuffer sb = new StringBuffer(accountCode);
					StringBuffer insert1 = sb.insert(4, '0');
					StringBuffer insert2 = insert1.insert(8, '0');
					StringBuffer insert3 = insert2.insert(12, '0');
					String newAccountCode = new String(insert3);
					account.setAccountCode(newAccountCode);

				}
				// 判断如果数据库的code等于设置的对象code，则对象code递增
				if (account.getAccountCode().equals(sqlCode)) {

					int parseInt = Integer.parseInt(account.getAccountCode()) + 1;
					if (parseInt != Integer.parseInt(sqlCode)) {
						account.setAccountCode(String.valueOf(parseInt));

					}
				}
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			JdbcUtil.closeAll(rs, ps, conn);
		}
		return account.getAccountCode();

	}

	/**
	 * 去除重复的摘要
	 * 
	 * @param voucherAbstract
	 * @param debitAccount
	 * @param creditAccount
	 * @return
	 * @throws Exception
	 */
	public static int getRepeat(String voucherAbstract, String voucher_code, String debitAccount, String creditAccount)
			throws Exception {

		int i = 0;
		/**
		 * 从数据库判断是否有相同的摘要+借贷关系
		 */
		String sql = "select count(1) from comparison_abstract WHERE (voucher_abstract='" + voucherAbstract + ""
				+ "' and debit_account='" + debitAccount + "')OR (voucher_abstract='" + voucherAbstract
				+ "' and credit_account='" + creditAccount + "')";

		try {
			rs = JdbcUtil.getConn().prepareStatement(sql).executeQuery();
			while (rs.next()) {
				i = rs.getInt("COUNT(1)");
			}
			return i;
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			JdbcUtil.closeAll(rs, ps, conn);
		}
		return i;

	}

	/**
	 * 去除小数点及其统一编码
	 * 
	 * @param accountCode
	 * @return
	 */
	public static String getAccountCode(String accountCode) {
		ComparisonAccount account = new ComparisonAccount();
		account.setAccountCode(accountCode.replaceAll("[p{P}+~$`^=|<>～｀＄＾＋＝｜—＜＞（）-￥×]", ""));
		// 去掉小数点的科目编码
		if (account.getAccountCode().contains(".")) {
			String replaceCode = account.getAccountCode().replaceAll("[.]", "");
			account.setAccountCode(replaceCode);
		}
		// 判断如果是2级的和科目编码等于6位数，变成8位数
		if (account.getAccountCode().length() == 6) {

			StringBuffer sb = new StringBuffer(account.getAccountCode());
			StringBuffer insert1 = sb.insert(4, new char[] { '0', '0' });
			String newAccountCode = new String(insert1);
			account.setAccountCode(newAccountCode);
			// 判断如果是2级的和科目编码等于7位数，变成8位数
		} else if (account.getAccountCode().length() == 7) {

			StringBuffer sb = new StringBuffer(account.getAccountCode());
			StringBuffer insert1 = sb.insert(4, '0');
			String newAccountCode = new String(insert1);
			account.setAccountCode(newAccountCode);
			// 判断如果是3级的和科目编码等于8位数，变成12位数
		} else if (account.getAccountCode().length() == 8) {
			if (account.getAccountCode().substring(4, 6).equals("00")) {
				return accountCode;
			} else {
				StringBuffer sb = new StringBuffer(account.getAccountCode());
				StringBuffer insert1 = sb.insert(4, new char[] { '0', '0' });
				StringBuffer insert2 = insert1.insert(8, new char[] { '0', '0' });
				String newAccountCode = new String(insert2);
				account.setAccountCode(newAccountCode);
			}
			// 判断如果是3级的和科目编码等于9位数，变成12位数
		} else if (account.getAccountCode().length() == 9) {
			if (account.getAccountCode().substring(4, 5).equals("0")
					&& account.getAccountCode().substring(6, 7).equals("0")) {

				StringBuffer sb = new StringBuffer(account.getAccountCode());
				StringBuffer insert1 = sb.insert(4, new char[] { '0', '0' });
				StringBuffer insert2 = insert1.insert(8, '0');
				String newAccountCode = new String(insert2);
				account.setAccountCode(newAccountCode);
			}
			// 判断如果是3级的和科目编码等于10位数，变成12位数
		} else if (account.getAccountCode().length() == 10) {
			if (account.getAccountCode().substring(4, 5).equals("0")
					&& account.getAccountCode().substring(7, 8).equals("0")) {
				StringBuffer sb = new StringBuffer(account.getAccountCode());
				StringBuffer insert1 = sb.insert(4, '0');
				StringBuffer insert2 = insert1.insert(8, '0');
				String newAccountCode = new String(insert2);
				account.setAccountCode(newAccountCode);
			} else {
				StringBuffer sb = new StringBuffer(account.getAccountCode());
				StringBuffer insert1 = sb.insert(4, new char[] { '0', '0' });
				StringBuffer insert2 = insert1.insert(8, new char[] { '0', '0' });
				StringBuffer insert3 = insert2.insert(12, new char[] { '0', '0' });
				String newAccountCode = new String(insert3);
				account.setAccountCode(newAccountCode);
			}

			// 判断如果是4级的和科目编码等于10位数，变成16位数
		}
		// 判断如果是4级的和科目编码等于13位数，变成16位数
		else if (account.getAccountCode().length() == 13) {

			StringBuffer sb = new StringBuffer(account.getAccountCode());
			StringBuffer insert1 = sb.insert(4, '0');
			StringBuffer insert2 = insert1.insert(8, '0');
			StringBuffer insert3 = insert2.insert(12, '0');
			String newAccountCode = new String(insert3);
			account.setAccountCode(newAccountCode);

		}
		return account.getAccountCode();

	}

	/**
	 * 获取comparison_abstract表voucher_code的最大值
	 * 
	 * @return
	 */
	public static int getVoucherCode() {

		String sql = "SELECT MAX(CAST(voucher_code as UNSIGNED INTEGER)) FROM comparison_abstract";
		try {
			rs = JdbcUtil.getConn().prepareStatement(sql).executeQuery();
			int i = 0;
			while (rs.next()) {
				i = rs.getInt("MAX(CAST(voucher_code as UNSIGNED INTEGER))");
				return i;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return 0;

	}

	/**
	 * 查询abstract_detail表voucher_code重复
	 * 
	 * @param code
	 * @return
	 * @throws SQLException
	 */
	public static int getVoucherCode(String code) throws SQLException {
		String sql = "select count(1) from abstract_detail where voucher_code='" + code + "'";
		rs = JdbcUtil.getConn().prepareStatement(sql).executeQuery();
		int i = 0;
		while (rs.next()) {
			i = rs.getInt("count(1)");
			return i;
		}
		return 0;

	}

	public static void main(String[] args) throws SQLException {

		String accountCode = getAccountCode("22110.030.01");
		System.out.println(accountCode);
	}
}
