package com.transfar.hr.userinfo;

public class Experience {
	private String startTime;
	private String endTime;
	private String company;
	private String department;
	private String position;
	private String highestPosition;
	private String describe;
	private String subordinatesNumber;
	private String achievements; //10
	private String monthAveSalary;
	private String supervisorName;
	private String superiorPostion;
	private String superiorTelphone;
	private String enterpriseScale;
	private String enterpriseType;
	private String employeeNumber;
	private String zipCode;
	private String ReferencesAndTelephone;
	private String products;
	private String resignReason;	//20
	
	
	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	public String getCompany() {
		return company;
	}


	public void setCompany(String company) {
		this.company = company;
	}


	public String getDepartment() {
		return department;
	}


	public void setDepartment(String department) {
		this.department = department;
	}


	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	public String getHighestPosition() {
		return highestPosition;
	}


	public void setHighestPosition(String highestPosition) {
		this.highestPosition = highestPosition;
	}


	public String getDescribe() {
		return describe;
	}


	public void setDescribe(String describe) {
		this.describe = describe;
	}


	public String getSubordinatesNumber() {
		return subordinatesNumber;
	}


	public void setSubordinatesNumber(String subordinatesNumber) {
		this.subordinatesNumber = subordinatesNumber;
	}


	public String getAchievements() {
		return achievements;
	}


	public void setAchievements(String achievements) {
		this.achievements = achievements;
	}


	public String getMonthAveSalary() {
		return monthAveSalary;
	}


	public void setMonthAveSalary(String monthAveSalary) {
		this.monthAveSalary = monthAveSalary;
	}


	public String getSupervisorName() {
		return supervisorName;
	}


	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}


	public String getSuperiorPostion() {
		return superiorPostion;
	}


	public void setSuperiorPostion(String superiorPostion) {
		this.superiorPostion = superiorPostion;
	}


	public String getSuperiorTelphone() {
		return superiorTelphone;
	}


	public void setSuperiorTelphone(String superiorTelphone) {
		this.superiorTelphone = superiorTelphone;
	}


	public String getEnterpriseScale() {
		return enterpriseScale;
	}


	public void setEnterpriseScale(String enterpriseScale) {
		this.enterpriseScale = enterpriseScale;
	}


	public String getEnterpriseType() {
		return enterpriseType;
	}


	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
	}


	public String getEmployeeNumber() {
		return employeeNumber;
	}


	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}


	public String getZipCode() {
		return zipCode;
	}


	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}


	public String getReferencesAndTelephone() {
		return ReferencesAndTelephone;
	}


	public void setReferencesAndTelephone(String referencesAndTelephone) {
		ReferencesAndTelephone = referencesAndTelephone;
	}


	public String getProducts() {
		return products;
	}


	public void setProducts(String products) {
		this.products = products;
	}


	public String getResignReason() {
		return resignReason;
	}


	public void setResignReason(String resignReason) {
		this.resignReason = resignReason;
	}


	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o instanceof Experience){
			Experience si = (Experience)o;
			if(this.startTime.equals(si.startTime)
					&&this.endTime.equals(si.endTime)
					&&this.company.equals(si.company)
					&&this.department.equals(si.department)
					&&this.position.equals(si.position)
					&&this.highestPosition.equals(si.highestPosition)
					&&this.describe.equals(si.describe)
					&&this.subordinatesNumber.equals(si.subordinatesNumber)
					&&this.achievements.equals(si.achievements)
					&&this.monthAveSalary.equals(si.monthAveSalary)
					&&this.supervisorName.equals(si.supervisorName)
					&&this.superiorPostion.equals(si.superiorPostion)
					&&this.superiorTelphone.equals(si.superiorTelphone)
					&&this.enterpriseScale.equals(si.enterpriseScale)
					&&this.enterpriseType.equals(si.enterpriseType)
					&&this.employeeNumber.equals(si.employeeNumber)
					&&this.zipCode.equals(si.zipCode)
					&&this.ReferencesAndTelephone.equals(si.ReferencesAndTelephone)
					&&this.products.equals(si.products)
					&&this.resignReason.equals(si.resignReason)){
				return true;
			}
		}
		return false;
	}
	
}
																			
