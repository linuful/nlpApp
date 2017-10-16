package com.transfar.hr.userinfo;

public class FamilyMember {
	private String relationShips;
	private String name;
	private String birthDay;
	private String politics;
	private String education;
	private String workUnit;
	private String qualifications;
	private String position;
	
	
	public String getRelationShips() {
		return relationShips;
	}


	public void setRelationShips(String relationShips) {
		this.relationShips = relationShips;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getBirthDay() {
		return birthDay;
	}


	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}


	public String getPolitics() {
		return politics;
	}


	public void setPolitics(String politics) {
		this.politics = politics;
	}


	public String getEducation() {
		return education;
	}


	public void setEducation(String education) {
		this.education = education;
	}


	public String getWorkUnit() {
		return workUnit;
	}


	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}


	public String getQualifications() {
		return qualifications;
	}


	public void setQualifications(String qualifications) {
		this.qualifications = qualifications;
	}


	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o instanceof FamilyMember){
			FamilyMember si = (FamilyMember)o;
			if(this.relationShips.equals(si.relationShips)
					&&this.name.equals(si.name)
					&&this.politics.equals(si.politics)
					&&this.education.equals(si.education)
					&&this.workUnit.equals(si.workUnit)
					&&this.qualifications.equals(si.qualifications)
					&&this.position.equals(si.position)){
				return true;
			}
		}
		return false;
	}
}

