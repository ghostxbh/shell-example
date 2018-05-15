package cn.ssm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comparison_account")
public class ComparisonAccount {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "account_code")
	private String accountCode;

	@Column(name = "account_name")
	private String accountName;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "account_type")
	private String accountType;

	@Column(name = "account_direct")
	private String accountDirect;

	@Column(name = "account_parentId")
	private Integer accountParentId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountDirect() {
		return accountDirect;
	}

	public void setAccountDirect(String accountDirect) {
		this.accountDirect = accountDirect;
	}

	public Integer getAccountParentId() {
		return accountParentId;
	}

	public void setAccountParentId(Integer accountParentId) {
		this.accountParentId = accountParentId;
	}

	@Override
	public String toString() {
		return "ComparisonAccount [id=" + id + ", accountCode=" + accountCode + ", accountName=" + accountName
				+ ", fullName=" + fullName + ", accountType=" + accountType + ", accountDirect=" + accountDirect
				+ ", accountParentId=" + accountParentId + "]";
	}



}
