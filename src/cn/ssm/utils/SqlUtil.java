package cn.ssm.utils;

public class SqlUtil {
	/**
	 * 查询一个凭证号的借贷方向
	 * SELECT voucher_code from comparison_abstract GROUP BY voucher_code HAVING COUNT(voucher_code) < 2
	 * 
	 * 查询是否包含null的凭证号
	 * SELECT voucher_code from comparison_abstract WHERE debit_account='null' or credit_account='null'
	 * 
	 * 连表查询，编号连表
	 * select a.*,b.full_name from comparison_abstract a LEFT JOIN comparison_account b on a.debit_account = b.account_code or a.credit_account = b.account_code
	 * 
	 */
}
