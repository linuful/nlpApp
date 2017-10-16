package com.transfar.hr.userinfo;

public class SelfIntroduce {
	private String skills;
	private String interests;
	private String selfReview;
	
	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getInterests() {
		return interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}

	public String getSelfReview() {
		return selfReview;
	}

	public void setSelfReview(String selfReview) {
		this.selfReview = selfReview;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o instanceof SelfIntroduce){
			SelfIntroduce si = (SelfIntroduce)o;
			if(this.skills.equals(si.skills)
					&&this.interests.equals(si.interests)
					&&this.selfReview.equals(si.selfReview)){
				return true;
			}
		}
		
		return false;
	}
	
}
// TODO add to Base ?
