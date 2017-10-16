package com.transfar.hr.userinfo;

public class ResignData{
	private String employeeId;
	private String name;
	private String reason;
	private String resignType;
	
	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getResignType() {
		return resignType;
	}

	public void setResignType(String resignType) {
		this.resignType = resignType;
	}

	@Override
	public boolean equals(Object o){
		if(this == o)return true;
		
		if(o instanceof ResignData){
			ResignData r = (ResignData)o;
			if(this.employeeId.equals(r.employeeId))
				return true;
			
			if(this.name.equals(r.name)
					&&this.reason.equals(r.reason)
					&&this.resignType.equals(r.resignType))
				return true;
		}
		return false;
	}
}