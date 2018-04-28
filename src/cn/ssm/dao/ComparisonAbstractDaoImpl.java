package cn.ssm.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.ssm.entity.ComparisonAbstract;
import cn.ssm.utils.JdbcUtil;



public class ComparisonAbstractDaoImpl implements ComparisonAbstractDao {
	@Autowired
	private static ComparisonAbstractDao comparisonAbstractDao;	
	

	@Override
	public int insert(ComparisonAbstract comparisonAbstract) {
		//comparisonAbstract
		return 0;		
	}

}
