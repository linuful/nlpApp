package com.transfar.hr.userinfo;

public class Language {
	private String type;
	private String grade;
	private String acquireTime;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getAcquireTime() {
		return acquireTime;
	}

	public void setAcquireTime(String acquireTime) {
		this.acquireTime = acquireTime;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o instanceof Language){
			Language si = (Language)o;
			if(this.type.equals(si.type)
					&&this.grade.equals(si.grade)
					&&this.acquireTime.equals(si.acquireTime)){
				return true;
			}
		}
		return false;
	}
}
