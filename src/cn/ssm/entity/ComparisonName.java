package cn.ssm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comparison_name")
public class ComparisonName {

	@Column(name = "account_name")
	private String accountName;

	@Column(name = "account_id")
	private Integer accountId;

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	@Override
	public String toString() {
		return "ComparisonName [accountName=" + accountName + ", accountId=" + accountId + "]";
	}

}
