package com.transfar.hr.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author admin
 *
 */
public class Employee {
	private String employeeId;//工号
	private String name;//姓名
	private String company;//公司
	private String department;//部门
	private String position;//职位
	private String positionLevel;//职层
	private String positionGrade;//职级
	private String postionType;//岗位类别
	private String isBackbone;//核心骨干
	private Date birthDate = new Date();//出生日期
	private String educationLevel;//学历
	private String sex;//性别
	private String maritalStatus;//婚姻状况
	private Date joinTime;//入司时间
	private String joinRole;//入司角色
	private String joinSource;//入职来源
	private String registeredResidence;//户籍所在地
	private String status;// 当前状态  	在职 ?
	
	
	//other fields
	
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

	public String getPositionLevel() {
		return positionLevel;
	}

	public void setPositionLevel(String positionLevel) {
		this.positionLevel = positionLevel;
	}

	public String getPositionGrade() {
		return positionGrade;
	}

	public void setPositionGrade(String positionGrade) {
		this.positionGrade = positionGrade;
	}

	public String getPostionType() {
		return postionType;
	}

	public void setPostionType(String postionType) {
		this.postionType = postionType;
	}

	public String getIsBackbone() {
		return isBackbone;
	}

	public void setIsBackbone(String isBackbone) {
		this.isBackbone = isBackbone;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Date getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}

	public String getJoinRole() {
		return joinRole;
	}

	public void setJoinRole(String joinRole) {
		this.joinRole = joinRole;
	}

	public String getJoinSource() {
		return joinSource;
	}

	public void setJoinSource(String joinSource) {
		this.joinSource = joinSource;
	}

	public String getRegisteredResidence() {
		return registeredResidence;
	}

	public void setRegisteredResidence(String registeredResidence) {
		this.registeredResidence = registeredResidence;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setBirthDate(String strDate){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		 try {
			 this.birthDate = format.parse(strDate);
		 	} 
		 catch (ParseException e) {
			 Calendar calendar=Calendar.getInstance();
			 calendar.set(1900, 1,1);
			 this.birthDate = calendar.getTime();
			 }
	}
	
	public String getBirthdate(){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		return  format.format(this.birthDate);		
	}
	
	public void setJoinDate(String strDate){
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMM");
  		 try {
  			this.joinTime=format1.parse(strDate);
  		 } 
  		 catch (ParseException e) {
			 Calendar calendar=Calendar.getInstance();
			 calendar.set(1900, 1,1);
			 this.joinTime=calendar.getTime();
  	
  		 }
	}
	
	public String getJoinDate(){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		return  format.format(this.joinTime);	
	}
	
	public int getAge(){
		Calendar cur = Calendar.getInstance();
		Calendar birth = Calendar.getInstance();
		cur.setTime(new Date());
		birth.setTime(this.birthDate);
		return cur.get(Calendar.YEAR)-birth.get(Calendar.YEAR);
	}
	
	/** 
	 * 
	 * @return number of month
	 */
	public int getOfficeTeam(){
		Calendar cur = Calendar.getInstance();
		Calendar join = Calendar.getInstance();
		cur.setTime(new Date());
		join.setTime(this.joinTime);

		int months =cur.get(Calendar.YEAR)>join.get(Calendar.YEAR) ? 
				12*(cur.get(Calendar.YEAR)-join.get(Calendar.YEAR)) + cur.get(Calendar.MONTH)+12-join.get(Calendar.MONTH)
				: cur.get(Calendar.MONTH)-join.get(Calendar.MONTH);
		return months;
	}
																																										

	@Override
	public boolean equals(Object o){
		if(this==o){
			return true;
		}
		
		if(o instanceof Employee){
			Employee e = (Employee)o;
			if(this.employeeId.equals(e.employeeId))
				return true;
			
			if(this.name.equals(e.name)
					//TODO && other
					){
				return true;
				
			}
		}
		
		return false;
	}
	//TODO some other values;
	/*
	 * id
	 * salary
	 * member value
	 * salary expectation
	 * resign expectation
	 * */
}
