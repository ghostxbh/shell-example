package cn.ssm.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;
import org.springframework.stereotype.Repository;

import cn.ssm.entity.ComparisonAccount;
import cn.ssm.utils.JdbcUtil;

@Repository
public class ComparisonAccountDaoImpl implements ComparisonAccountDao {

	/**
	 * 查询所有
	 */
	@Override
	public List<ComparisonAccount> queryAll() {
		List<ComparisonAccount> accList = new ArrayList<ComparisonAccount>();
		String sql = "select * from comparison_account";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = JdbcUtil.getConn().prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				ComparisonAccount account = new ComparisonAccount();
				account.setId(rs.getInt("id"));
				account.setAccountCode(rs.getString("account_code"));
				account.setAccountName(rs.getString("account_name"));
				account.setFullName(rs.getString("full_name"));
				account.setAccountType(rs.getString("account_type"));
				account.setAccountDirect(rs.getString("account_direct"));
				accList.add(account);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.closeAll(rs, ps, conn);
		}

		return accList;

	}

	public static void main(String[] args) throws Exception {
		ComparisonAccountDaoImpl cadi = new ComparisonAccountDaoImpl();
		List<ComparisonAccount> list = cadi.queryAll();
		for (ComparisonAccount ca : list) {
			System.out.println(ca.toString());
		}
	}
}
