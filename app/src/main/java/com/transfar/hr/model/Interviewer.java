package com.transfar.hr.model;

import java.util.ArrayList;
import java.util.List;

import com.transfar.hr.userinfo.Base;
import com.transfar.hr.userinfo.Education;
import com.transfar.hr.userinfo.EmergencyContact;
import com.transfar.hr.userinfo.Experience;
import com.transfar.hr.userinfo.FamilyMember;
import com.transfar.hr.userinfo.Language;
import com.transfar.hr.userinfo.Project;
import com.transfar.hr.userinfo.SelfIntroduce;
import com.transfar.hr.userinfo.Skill;
import com.transfar.hr.userinfo.Training;

public class Interviewer {
	public Interviewer(){
		base = new Base();
		si = new SelfIntroduce();
		edus = new ArrayList<Education>();
		ecs = new ArrayList<EmergencyContact>();
		exps =  new ArrayList<Experience>();
		family = new ArrayList<FamilyMember>();
		langs = new ArrayList<Language>();
		projs = new ArrayList<Project>();
		sks = new ArrayList<Skill>();
		trs = new ArrayList<Training>();
	}
	
	public Base getBase(){
		return this.base;
	}
	public void setBase(Base base){
		this.base = base;
	}
	
	public List<Education> getEducations(){
		return this.edus;
	}
	
	public boolean addEducations(Education edu){
		if(null==edu)return false;
		for(Education e:this.edus)if(e.equals(edu))return false;
		this.edus.add(edu);
		return true;
	}
	
	public boolean addEmergencyContacts(EmergencyContact ec){
		if(null==ec)return false;
		for(EmergencyContact e:this.ecs)if(e.equals(ec))return false;
		this.ecs.add(ec);
		return true;
	}
	
	public List<EmergencyContact> getEmergencyContacts(){
		return this.ecs;
	}
	
	public boolean addExperiences(Experience exp){
		if(null==exp)
			return false;
		for(Experience ex:this.exps)if(ex.equals(exp))return false;
		this.exps.add(exp);
		return true;
	}
	
	public List<Experience> getExperiences(){
		return this.exps;
	}
	
	public boolean addFamilyMember(FamilyMember fa){
		if(null==fa)return false;
		for(FamilyMember f:this.family)if(f.equals(fa))return false;
		this.family.add(fa);
		return true;
	}
	
	public List<FamilyMember> getFamilyMember(){
		return this.family;
	}
	
	public boolean addLanguages(Language lang){
		if(null==lang)return false;
		for(Language lan:this.langs)if(lan.equals(lang))return false;
		this.langs.add(lang);
		return true;
	}
	
	public List<Language> getLanguages(){
		return this.langs;
	}
	
	public boolean addProjects(Project proj){
		if(null==proj)return false;
		for(Project pro:this.projs)if(pro.equals(proj))return false;
		this.projs.add(proj);
		return true;
	}
	
	public List<Project> getProjects(){
		return this.projs;
	}
	
	
	public boolean addSkills(Skill sk){
		if(null==sk)return false;
		for(Skill s:this.sks)if(sk.equals(s))return false;
		this.sks.add(sk);
		return true;
	}
	
	public List<Skill> getSkills(){
		return this.sks;
	}
	
	public boolean addTrainings(Training tr){
		if(null==tr)return false;
		for(Training t:this.trs)if(t.equals(t))return false;
		this.trs.add(tr);
		return true;
	}
	
	public List<Training> getTranings(){
		return this.trs;
	}
	
	public void setPersonId(String personId){
		this.personId = personId;
	}
	
	public String getPersonId(){
		return this.personId;
	}
	
	public void setSelfIntroduce(SelfIntroduce si){
		this.si=si;
	}
	public SelfIntroduce getSelfIntroduce(){
		return this.si;
	}
	
	private String personId;
	private	Base base = null;
	private SelfIntroduce si = null;
	private List<Education> edus = null;
	private List<EmergencyContact> ecs = null;
	private List<Experience> exps = null;
	private List<FamilyMember> family = null;
	private List<Language> langs = null;//
	private List<Project> projs = null;
	private List<Skill> sks = null;
	private List<Training> trs = null;
}
