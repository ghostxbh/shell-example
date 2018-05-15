package cn.ssm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comparison_abstract")
public class ComparisonAbstract {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "voucher_abstract")
	private String voucherAbstract;
	
	@Column(name = "voucher_code")
	private String voucherCode;

	@Column(name = "service_name")
	private String serviceName;
	
	@Column(name = "debit_account")
	private String debitAccount;

	@Column(name = "credit_account")
	private String creditAccount;

	@Column(name = "createdTime")
	private Date createdTime;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVoucherAbstract() {
		return voucherAbstract;
	}

	public void setVoucherAbstract(String voucherAbstract) {
		this.voucherAbstract = voucherAbstract;
	}

	public String getDebitAccount() {
		return debitAccount;
	}

	public void setDebitAccount(String debitAccount) {
		this.debitAccount = debitAccount;
	}

	public String getCreditAccount() {
		return creditAccount;
	}

	public void setCreditAccount(String creditAccount) {
		this.creditAccount = creditAccount;
	}
	
	public String getVoucherCode() {
		return voucherCode;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Override
	public String toString() {
		return "ComparisonAbstract [id=" + id + ", voucherAbstract=" + voucherAbstract + ", voucherCode=" + voucherCode
				+ ", serviceName=" + serviceName + ", debitAccount=" + debitAccount + ", creditAccount=" + creditAccount
				+ ", createdTime=" + createdTime + "]";
	}	

	

}
