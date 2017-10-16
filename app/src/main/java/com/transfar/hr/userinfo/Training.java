package com.transfar.hr.userinfo;

public class Training {
	private String startTime;
	private String endTime;
	private String institution;
	private String context;
	
	
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


	public String getInstitution() {
		return institution;
	}


	public void setInstitution(String institution) {
		this.institution = institution;
	}


	public String getContext() {
		return context;
	}


	public void setContext(String context) {
		this.context = context;
	}


	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o instanceof Training){
			Training t = (Training)o;
			if(this.startTime.equals(t.startTime)
					&&this.endTime.equals(t.endTime)
					&&this.institution.equals(t.institution)
					&&this.context.equals(t.context)){
				return true;
			}
		}
		return false;
	}
}
