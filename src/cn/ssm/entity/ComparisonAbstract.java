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

	
	@Column(name = "debit_account")
	private String debitAccount;

	@Column(name = "credit_account")
	private String creditAccount;

	@Column(name = "created_time")
	private Date created_time;
	
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

	public Date getCreated_time() {
		return created_time;
	}

	public void setCreated_time(Date created_time) {
		this.created_time = created_time;
	}

	@Override
	public String toString() {
		return "ComparisonAbstract [id=" + id + ", voucherAbstract=" + voucherAbstract + ", voucherCode=" + voucherCode
				+ ", debitAccount=" + debitAccount + ", creditAccount=" + creditAccount + ", created_time="
				+ created_time + "]";
	}

}
