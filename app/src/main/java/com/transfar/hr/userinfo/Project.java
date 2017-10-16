package com.transfar.hr.userinfo;

public class Project {
	private String startTime;
	private String endTime;
	private String name;
	private String describe;
	private String responsibility;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o instanceof Project){
			Project p = (Project)o;
			if(this.startTime.equals(p.startTime)
					&&this.endTime.equals(p.endTime)
					&&this.name.equals(p.name)
					&&this.describe.equals(p.describe)
					&&this.responsibility.equals(p.responsibility))
				return true;
		}
		return false;
	}
}
																																		
