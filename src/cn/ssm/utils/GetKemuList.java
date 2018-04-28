package cn.ssm.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	public static Integer getParentId(String accountCode, String fullName, String accountName) throws Exception {

		String sql = "select * from comparison_account";
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
				account.setFullName(rs.getString("full_name"));
				account.setAccountType(rs.getString("account_type"));
				account.setAccountDirect(rs.getString("account_direct"));
				account.setAccountParentId(rs.getInt("account_parentId"));
				String codeName = fullName.substring(0, 4);//截取一级科目名称
				/**
				 * 判断是否包含一级科目名字的字段
				 */
				if (account.getAccountName().equals(account.getFullName().contains(codeName))) {
					i = account.getId();

				} else if (account.getAccountName().equals(codeName) | account.getAccountCode().equals(accountCode)) {
					i = account.getId();

				} else if (account.getAccountName().contains(codeName)) {
					i = account.getId();

				}
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
	public static String getAccountCode(String accountCode) throws Exception {

		String sql = "select account_code from comparison_account";
		ComparisonAccount account = new ComparisonAccount();
		try {
			rs = JdbcUtil.getConn().prepareStatement(sql).executeQuery();
			while (rs.next()) {
				String sqlCode = rs.getString("account_code");
				// 如果数据库中的accountCode与Excle表里面的Code相等，直接跳过
				if (sqlCode.equals(accountCode)) {
					continue;
				}
				account.setAccountCode(accountCode);
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
			//JdbcUtil.closeAll(rs, ps, conn);
		}
		return i;

	}

}
