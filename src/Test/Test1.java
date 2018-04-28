package Test;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.ssm.dao.ComparisonAccountDao;
import cn.ssm.dao.ComparisonAccountDaoImpl;
import cn.ssm.entity.ComparisonAccount;
import junit.framework.TestCase;

public class Test1 extends TestCase {
	private ComparisonAccountDao dao;
	private ApplicationContext ctx;
	private JdbcTemplate jdbcTemplate;
	
	
	
	public void before(){
		ctx=new ClassPathXmlApplicationContext(new String[]{"spring-dao.xml"});
		ctx.getBean("jdbcTemplate",JdbcTemplate.class);
		ctx.getBean("comparisonAccountDaoImpl",ComparisonAccountDaoImpl.class);
	}
	
	
	public void test(){
		String sql = "select * from comparison_account";
		ComparisonAccount account = new ComparisonAccount();
		List<ComparisonAccount> accList =  new ArrayList<ComparisonAccount>();
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		for (Map<String, Object> map : list) {
			for (Map.Entry<String, Object> map1 : map.entrySet()) {
				String key = map1.getKey().toString();
				String value = map1.getValue().toString();
				if (key.equals("account_name")) {
					account.setAccountName(value);
				}
				if (key.equals("id")) {
					account.setId(Integer.parseInt(value));
				}
				if (key.equals("account_type")) {
					//account.setAccountType(Integer.parseInt(value));
				}
				if (key.equals("account_direct")) {
					//account.setAccountDirect(Integer.parseInt(value));
				}
				
				
			}
			accList.add(account);
			
		}
		
		dao.queryAll();
	}
}
