package net.bgsystems.util.web;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LogUser implements Serializable {
	@Column(name = "NAME")
	private String name;
	@Column(name = "WORK_CENTER")
	private String workCenter;
	@Column(name = "COST_CENTER")
	private String costCenter;

	public LogUser() {
		// TODO Auto-generated constructor stub
	}

	public LogUser(String name, String workCenter, String costCenter) {
		this.name = name;
		this.workCenter = workCenter;
		this.costCenter = costCenter;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWorkCenter() {
		return workCenter;
	}

	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
	}
}
