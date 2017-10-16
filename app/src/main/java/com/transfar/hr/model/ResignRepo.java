package com.transfar.hr.model;

import java.util.ArrayList;
import java.util.List;

import com.transfar.hr.userinfo.ResignData;

public class ResignRepo {
	public ResignRepo(){
		repo = new ArrayList<ResignData>();
	}
	
	public List<ResignData> get(){
		return repo;	
	}
	
	public boolean add(ResignData d){
		boolean b = true;
		if(d==null)return !b;
		
		for(ResignData rd:this.repo){
			if(rd.equals(d)){
				b = false;
				break;
			}
		}
		if(b)
			repo.add(d);
		return b;
	}
	
	private List<ResignData> repo;
}

