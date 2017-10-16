package com.transfar.hr.userinfo;

public class EmergencyContact {
	private String name;
	private String telphone;
	private String residencePlace;
	private String zipCode;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getResidencePlace() {
		return residencePlace;
	}

	public void setResidencePlace(String residencePlace) {
		this.residencePlace = residencePlace;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o instanceof EmergencyContact){
			EmergencyContact si = (EmergencyContact)o;
			if(this.name.equals(si.name)
					&&this.telphone.equals(si.telphone)
					&&this.residencePlace.equals(si.residencePlace)
					&&this.zipCode.equals(si.zipCode)){
				return true;
			}
		}
		return false;
	}
}
