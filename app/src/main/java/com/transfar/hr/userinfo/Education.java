package com.transfar.hr.userinfo;

public class Education {

	private String startTime;
	private String endTime;
	private String college;
	private String degree;
	private String level;	//学历
	private String major;
	private String minor;
	
	
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


	public String getCollege() {
		return college;
	}


	public void setCollege(String college) {
		this.college = college;
	}


	public String getDegree() {
		return degree;
	}


	public void setDegree(String degree) {
		this.degree = degree;
	}


	public String getLevel() {
		return level;
	}


	public void setLevel(String level) {
		this.level = level;
	}


	public String getMajor() {
		return major;
	}


	public void setMajor(String major) {
		this.major = major;
	}


	public String getMinor() {
		return minor;
	}


	public void setMinor(String minor) {
		this.minor = minor;
	}


	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o instanceof Education){
			Education si = (Education)o;
			if(this.startTime.equals(si.startTime)
					&&this.endTime.equals(si.endTime)
					&&this.college.equals(si.college)
					&&this.degree.equals(si.degree)
					&&this.level.equals(si.level)
					&&this.major.equals(si.major)
					&&this.minor.equals(si.minor)){
				return true;
			}
		}
		return false;
	}
}
