package cn.ssm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "abstract_detail")
public class AbstractDetail {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "voucher_code")
	private String voucherCode;

	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "department_name")
	private String departmentName;
	
	@Column(name = "ticket_type")
	private String ticketType;

	@Column(name = "voucher_count")
	private String voucherCount;

	@Column(name = "priority")
	private String priority;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVoucherCode() {
		return voucherCode;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getVoucherCount() {
		return voucherCount;
	}

	public void setVoucherCount(String voucherCount) {
		this.voucherCount = voucherCount;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	@Override
	public String toString() {
		return "AbstractDetail [id=" + id + ", voucherCode=" + voucherCode + ", companyName=" + companyName
				+ ", departmentName=" + departmentName + ", ticketType=" + ticketType + ", voucherCount=" + voucherCount
				+ ", priority=" + priority + "]";
	}

	

}
