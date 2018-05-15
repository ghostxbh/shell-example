package Test;


import java.io.IOException;
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
import cn.ssm.entity.AbstractDetail;
import cn.ssm.entity.ComparisonAccount;
import cn.ssm.utils.GetKemuList;
import cn.ssm.web.Test_import_abstract;
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
	
	
	public void test() throws IOException{
		//Integer maxvCode = GetKemuList.getVoucherCode();
		//int maxCode = GetKemuList.getMaxCode();
		AbstractDetail ad = new AbstractDetail();
		for (int k = 55510; k <= 56476; k++) {
			//maxCode = GetKemuList.getMaxCode();
			ad.setCompanyName("淄博润义金环保新材料科技有限公司");// 设置公司名称
			ad.setVoucherCode(String.valueOf(k));
			ad.setPriority("2");
		/*	try {
				if (GetKemuList.getVoucherCode(ad.getVoucherCode()) < 1) {
					ad.setVoucherCode(String.valueOf(k));
				} else {
					continue;
				}
			} catch (Exception e) {  

				e.printStackTrace();
			}*/
			int detail = Test_import_abstract.insertDetail(ad);
			if (detail != 0) {
				System.out.println("添加凭证表成功");
			} else {
				System.out.println("添加凭证表失败");
			}
			System.out.println(ad.toString());
		}
	}
}
