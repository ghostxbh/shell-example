package cn.ssm.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cn.ssm.entity.ComparisonAccount;

public class MyRowMapper implements RowMapper<ComparisonAccount> {

	/*public ComparisonAccount mapRow(ResultSet rs,int id,String name) throws SQLException {
		ComparisonAccount account = new ComparisonAccount();
		account.setId(rs.getInt(id));
		account.setAccountName(rs.getString(name));
		return account;
	}*/

	@Override
	public ComparisonAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
		ComparisonAccount account = new ComparisonAccount();
		//account.setId(rs.getInt(columnLabel));
		return account;
	}

}
