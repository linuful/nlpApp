package com.transfar.hr.userinfo;

public class Skill {
	private String title;
	private String titleAcquireTime;
	private String technicalCategoriesAndGrades;
	private String technicalAcquireTime;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleAcquireTime() {
		return titleAcquireTime;
	}

	public void setTitleAcquireTime(String titleAcquireTime) {
		this.titleAcquireTime = titleAcquireTime;
	}

	public String getTechnicalCategoriesAndGrades() {
		return technicalCategoriesAndGrades;
	}

	public void setTechnicalCategoriesAndGrades(String technicalCategoriesAndGrades) {
		this.technicalCategoriesAndGrades = technicalCategoriesAndGrades;
	}

	public String getTechnicalAcquireTime() {
		return technicalAcquireTime;
	}

	public void setTechnicalAcquireTime(String technicalAcquireTime) {
		this.technicalAcquireTime = technicalAcquireTime;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o instanceof Skill){
			Skill s = (Skill)o;
			if(s.title.equals(this.title) 
					&& s.titleAcquireTime.equals(this.titleAcquireTime)
					&&s.technicalCategoriesAndGrades.equals(this.technicalCategoriesAndGrades)
					&&s.technicalAcquireTime.equals(this.technicalAcquireTime))
				return true;
		}
		return false;	
	}
}
