package com.transfar.hr.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;



public class EmployeeRepo {
	
	private Map<String,Employee> repo = null;
	
	
	public EmployeeRepo(){
		repo = new HashMap<String,Employee>();
	}
	
	public void clear(){
		repo.clear();
	}
	
	public int getSize(){
		return repo.size();
	}
	
	public Collection<Employee> getEmployees(){
		return this.repo.values();
	}
	
	public Employee getEmployee(String emplid) {
		return repo.get(emplid);
	}
	
	
	public boolean addEmployee(Employee emp) {
		if(null==emp) 
			return false;
		if(repo.containsKey(emp.getEmployeeId()))
			return false;
		//TODO compare other..
	
		this.repo.put(emp.getEmployeeId(), emp);
		
		return true;
	}
	
	public boolean replaceEmployee(Employee emp) {
		if(null==emp) 
			return false;
		if(repo.containsKey(emp.getEmployeeId())){
			repo.replace(emp.getEmployeeId(), emp);
		}
		
		return false;
	}

}
