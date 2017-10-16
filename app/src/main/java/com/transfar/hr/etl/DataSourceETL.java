package com.transfar.hr.etl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.transfar.es.ESClient;
import com.transfar.hr.config.Config;
import com.transfar.hr.dao.OracleJDBC;
import com.transfar.hr.model.Employee;
import com.transfar.hr.model.EmployeeRepo;
import com.transfar.util.ExcelUtil;

public class DataSourceETL {

	public static void main(String[] args) throws Exception{
		Config.getInstance();
	


		DataSourceETL etl = new DataSourceETL();
		etl.log=LogManager.getLogger();
		etl.getEmployeeDataFromOracle();
		String excelFile="output\\employee.xlsx";
		etl.writeEmployeeDataToExcel(excelFile);
		
		
		//etl.getEmployeeDataFromExcel(excelFile);
		//etl.addEmployeeDataToES();
		ESClient es=new ESClient();
		es.open();
		System.out.println(es.searchByEmployeeId("005561"));
		es.close();
	}
	
	final static String NOT_DEFINED="未定义";
	private Logger log = null;
	private EmployeeRepo repo=new EmployeeRepo();
	
	public void addEmployeeDataToES(){
		ESClient es=new ESClient();
		es.open();
		
		es.bulkProcess(repo.getEmployees());
		es.close();
	}
	
	
	public void getEmployeeDataFromExcel(String excelFile){
		repo.clear();
		
		Workbook wb = ExcelUtil.getWorkBook(excelFile);
		Sheet sheet = wb.getSheet("员工台账");
		for(int idx = sheet.getFirstRowNum()+1; idx <= sheet.getLastRowNum(); idx ++){
			Employee emp = new Employee();
			Row row = sheet.getRow(idx);
			for(int idxr = row.getFirstCellNum(); idxr<= row.getLastCellNum();idxr ++){
				Cell cell = row.getCell(idxr);
				String value=ExcelUtil.getCellValue(cell);
				switch(idxr){
				case 0:
					emp.setEmployeeId(value);
					break;
				case 1:
					emp.setName(value);
					break;
				case 2:
					emp.setCompany(value);
					break;
				case 3:
					emp.setDepartment(value);
					break;
				case 4:
					emp.setPosition(value);
					break;
				case 5:
					emp.setPositionLevel(value);
					break;
				case 6:
					emp.setPositionGrade(value);
					break;
				case 7:
					emp.setPostionType(value);
					break;
				case 8:
					emp.setIsBackbone(value);
					break;
				case 9:
					emp.setBirthDate(value);
					//emp.employeeId=value;
					break;
				case 10:
					emp.setEducationLevel(value);
					break;
				case 11:
					emp.setSex(value);
					break;
				case 12:
					emp.setMaritalStatus(value);
					break;
				case 13:
					emp.setJoinDate(value);
					break;
				case 14:
					emp.setJoinRole(value);
					break;
				case 15:
					emp.setJoinSource(value);
					break;
				case 16:
					emp.setRegisteredResidence(value);
					break;
				case 17:
					emp.setStatus(value);
					break;
				}
			}
			//System.out.println(emp.name+" "+emp.company+" " +emp.getAge());
			repo.addEmployee(emp);
		}
		log.info("employee size:"+repo.getSize());
	}
	

	public void writeEmployeeDataToExcel(String excelFile) throws IOException{
	
		if(null==excelFile)
			excelFile = Config.getInstance().getDesktopPath()+"output\\default.xlsx";
		
		Workbook wb = ExcelUtil.createWorkBook(excelFile);
		Sheet sheet = wb.createSheet("员工台账");

		Row titleRow = sheet.createRow(0);
		this.addTitle(titleRow, 0);
		
		int rowIdx=1;
		if(null!=repo)
			for (Employee emp : repo.getEmployees()) {
				Row row = sheet.createRow(rowIdx++);
				Cell cell = null;
				cell=row.createCell(0,CellType.STRING);
				cell.setCellValue(emp.getEmployeeId());
				
				cell=row.createCell(1,CellType.STRING);
				cell.setCellValue(emp.getName());
				
				cell=row.createCell(2,CellType.STRING);
				cell.setCellValue(emp.getCompany());
				
				cell=row.createCell(3,CellType.STRING);
				cell.setCellValue(emp.getDepartment());
				
				cell=row.createCell(4,CellType.STRING);
				cell.setCellValue(emp.getPosition());
				
				cell=row.createCell(5,CellType.STRING);
				cell.setCellValue(emp.getPositionLevel());
				
				cell=row.createCell(6,CellType.STRING);
				cell.setCellValue(emp.getPositionGrade());
				
				cell=row.createCell(7,CellType.STRING);
				cell.setCellValue(emp.getPostionType());
				
				cell=row.createCell(8,CellType.STRING);
				cell.setCellValue(emp.getIsBackbone());
				
				cell=row.createCell(9,CellType.STRING);
				cell.setCellValue(emp.getBirthdate());
				
				cell=row.createCell(10,CellType.STRING);
				cell.setCellValue(emp.getEducationLevel());
				
				cell=row.createCell(11,CellType.STRING);
				cell.setCellValue(emp.getSex());
				
				cell=row.createCell(12,CellType.STRING);
				cell.setCellValue(emp.getMaritalStatus());
				
				cell=row.createCell(13,CellType.STRING);
				cell.setCellValue(emp.getJoinDate());
				
				cell=row.createCell(14,CellType.STRING);
				cell.setCellValue(emp.getJoinRole());
				
				cell=row.createCell(15,CellType.STRING);
				cell.setCellValue(emp.getJoinSource());
				
				cell=row.createCell(16,CellType.STRING);
				cell.setCellValue(emp.getRegisteredResidence());
				
				cell=row.createCell(17,CellType.STRING);
				cell.setCellValue(emp.getStatus());
				
			}
		
		OutputStream out = new FileOutputStream(excelFile);
		wb.write(out);
		out.flush();
		out.close();
		

		
	}
	
	private void addTitle(Row row,int idx){
		Cell cell = null;
		cell=row.createCell(0,CellType.STRING);
		cell.setCellValue("工号");
		
		cell=row.createCell(1,CellType.STRING);
		cell.setCellValue("姓名");
		
		cell=row.createCell(2,CellType.STRING);
		cell.setCellValue("公司");
		
		cell=row.createCell(3,CellType.STRING);
		cell.setCellValue("部门");
		
		cell=row.createCell(4,CellType.STRING);
		cell.setCellValue("职位");
		
		cell=row.createCell(5,CellType.STRING);
		cell.setCellValue("职层");
		
		cell=row.createCell(6,CellType.STRING);
		cell.setCellValue("职级");
		
		cell=row.createCell(7,CellType.STRING);
		cell.setCellValue("岗位类别");
		
		cell=row.createCell(8,CellType.STRING);
		cell.setCellValue("核心骨干");
		
		cell=row.createCell(9,CellType.STRING);
		cell.setCellValue("出生日期");
		
		cell=row.createCell(10,CellType.STRING);
		cell.setCellValue("学历");
		
		cell=row.createCell(11,CellType.STRING);
		cell.setCellValue("性别");
		
		cell=row.createCell(12,CellType.STRING);
		cell.setCellValue("婚姻状况");
		
		cell=row.createCell(13,CellType.STRING);
		cell.setCellValue("入司时间");
		
		cell=row.createCell(14,CellType.STRING);
		cell.setCellValue("入司角色");
		
		cell=row.createCell(15,CellType.STRING);
		cell.setCellValue("入职来源");
		
		cell=row.createCell(16,CellType.STRING);
		cell.setCellValue("户籍所在地");
		
		cell=row.createCell(17,CellType.STRING);
		cell.setCellValue("当前状态");
	}
	
	
	public void getEmployeeDataFromOracle() throws ClassNotFoundException, SQLException{
		long l1 = System.currentTimeMillis();
		repo = new EmployeeRepo();
		Connection conn;

		conn = OracleJDBC.getHRConnection();
		
		OracleJDBC.dropTable(conn,"job_core_type_tmp");
		String sql = "create table job_core_type_tmp as ("
					+" select t.setid || t.jobcode jobcode_key, a.xlatlongname 核心骨干"
					+" from ps_jobcode_tbl t, PSXLATITEM a"
					+" where a.fieldname = 'KEY_JOBCODE'"
					+" and t.key_jobcode = a.fieldvalue)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.execute();
		ps.close();
		
		class JobClass{
			public String postion;//职位
			public String postionType;//岗位类别
			public String postionLvl; //职层
			public JobClass(String s1,String s2,String s3){
				this.postion=s1;this.postionType=s2;this.postionLvl=s3;
			}
		}
		Map<String,JobClass> jobClassMap = new HashMap<String,JobClass>();		
		sql = "select t.position_nbr, t.descr 职位, a.descr 岗位类别, b.xlatlongname 职层"
			+" from ps_position_data t, ps_hps_posn_tp_tbl a, PSXLATITEM b"
			+" where b.fieldname = 'HPS_JOB_CLASS'"
			+" and t.hps_posn_type = a.hps_posn_type"
			+" and t.hps_job_class = b.fieldvalue";
		ps=conn.prepareStatement(sql);
		ResultSet res = ps.executeQuery();
		while(res.next()){
			jobClassMap.put(res.getString("position_nbr"), new JobClass(res.getString("职位"),res.getString("岗位类别"),res.getString("职层")));
		}
		res.close();
		ps.close();
		log.debug("jobClassMap size:"+jobClassMap.size());
		
		
		//OracleJDBC.dropTable(conn,"in_service_tmp");
		sql="select j.emplid, count(j.emplid) 当前状态"
			+" from ps_job j"
			+" where j.action = 'TER'"
			+" group by j.emplid";
		Map<String,String> inserivceMap = new HashMap<String,String>();
		ps=conn.prepareStatement(sql);
		res = ps.executeQuery();
		while(res.next()){
			inserivceMap.put(res.getString("emplid"), "离职");
		}
		res.close();
		ps.close();
		log.debug("inserivceMap size:" +inserivceMap.size());
		 
		
		sql="select t.hps_posn_basic_lvl ,t.descr 职级 from ps_hps_basic_level t";
		Map<String,String> posnlvlMap = new HashMap<String,String>();	
		ps = conn.prepareStatement(sql);
		res = ps.executeQuery();
		while(res.next()){
			posnlvlMap.put(res.getString("hps_posn_basic_lvl"), res.getString("职级"));
		}
		res.close();
		ps.close();
		log.debug("posnlvlMap size:" + posnlvlMap.size());
		
		class JoinData{
			public String joinDate;
			public String joinSource;
			public JoinData(String d,String s){
				this.joinDate=d;this.joinSource=s;				
			}
		}
		Map<String,JoinData> joinMap = new HashMap<String,JoinData>();
		sql=" select t.emplid, to_char(t.effdt,'YYYYMM') 入司时间, j.descr 入职来源"
			+" from ps_job t,ps_actn_reason_tbl j,ps_personal_data k"
			+" where t.emplid = k.emplid"
			+" and t.action = 'HIR'"
			+" and t.action_reason = j.action_reason";
		ps = conn.prepareStatement(sql);
		res = ps.executeQuery();
		while(res.next()){
			joinMap.put(res.getString("emplid"),new JoinData(res.getString("入司时间"),res.getString("入职来源")));
		}
		res.close();
		ps.close();
		log.debug("joinMap size:" + joinMap.size());
		
		
		class JobInfo{
			public String keyJob;//核心骨干
			public String dept;
			public String company;
			public JobClass jc;//职位 岗位类别  职层
			public String inService;//当前状态
			public String posngrade;	//职级
			public JoinData jd;//入司时间 入司角色
			public JobInfo(){
				this.keyJob=NOT_DEFINED;
				this.dept=NOT_DEFINED;
				this.company=NOT_DEFINED;
				this.inService=NOT_DEFINED;
				this.posngrade=NOT_DEFINED;
				this.jc=new JobClass(NOT_DEFINED,NOT_DEFINED,NOT_DEFINED);
				this.jd = new JoinData(NOT_DEFINED, NOT_DEFINED);
			}
		}
		Map<String,JobInfo> jobMap = new HashMap<String,JobInfo>();
		sql=" select j.emplid,"
        +" b.核心骨干,"
        +" d.descr         部门,"
        +" e.descr         公司,"
        +" j.hps_job_level,"
        +" j.position_nbr"
        +" from ps_job            j,"
        +" job_core_type_tmp b,"
        +" ps_hps_dept_descr d,"
        +" ps_company_tbl    e"
        +" where j.empl_rcd = 0"
        +" and j.effdt = (select max(t.effdt)"
        +" from ps_job t"
        +" where t.emplid = j.emplid"
        +" and j.empl_rcd = t.empl_rcd)"
        +" and j.effseq = (select max(k.effseq)"
        +" from ps_job k"
        +" where k.emplid = j.emplid"
        +" and j.empl_rcd = k.empl_rcd"
        +" and j.effdt = k.effdt)"
        +" and j.setid_jobcode || j.jobcode = b.jobcode_key"
        +" and j.setid_dept || j.deptid = d.setid || d.deptid"
        +" and j.company = e.company";
		ps = conn.prepareStatement(sql);
		res = ps.executeQuery();
		while(res.next()){
			JobInfo ji = new JobInfo();
			ji.company = res.getString("公司");
			ji.dept = res.getString("部门");
			ji.keyJob = res.getString("核心骨干");
			
			//posnlvlMap
			String key = res.getString("hps_job_level");
			String value = posnlvlMap.get(key);
			if(value==null){
				value = NOT_DEFINED;
			}
			ji.posngrade=value;
			
			//jobClassMap
			key=res.getString("position_nbr");
			JobClass jcvalue = jobClassMap.get(key);
			if(null==jcvalue){
				jcvalue=new JobClass(NOT_DEFINED,NOT_DEFINED, NOT_DEFINED);
			}
			ji.jc=jcvalue;
			
			//inserivceMap
			key=res.getString("emplid");
			if(inserivceMap.containsKey(key)){
				ji.inService="离职";
			}else{
				ji.inService="在职";
			}
			//joinMap
			JoinData jdValue = joinMap.get(key);
			if(null==jdValue){
				jdValue = new JoinData(NOT_DEFINED, NOT_DEFINED);
			}
			ji.jd=jdValue;
			/*
			if(key.equals("000912"))
				log.debug(key+" "+ji.company+" "+ji.dept+" "+ji.inService+" "+ji.keyJob+" "+ji.posnLvl
					+" "+ji.jc.postion+" "+ji.jc.postionLvl+" "+ji.jc.postionType
					+" "+ji.jd.joinDate+" "+ji.jd.joinSource);
					*/
			jobMap.put(key, ji);
		}
		res.close();
		ps.close();
		log.debug("jobMap size:" + jobMap.size());
		
		/******************************************/
		
		sql="select t.contrib_area_chn,t.descr 户籍所在地 from ps_hukou_l_dtl_chn t";
		Map<String,String> hukouMap = new HashMap<String,String>();	
		ps = conn.prepareStatement(sql);
		res = ps.executeQuery();
		while(res.next()){
			hukouMap.put(res.getString("contrib_area_chn"), res.getString("户籍所在地"));
		}
		res.close();
		ps.close();
		log.debug("hukouMap size:"+hukouMap.size());
		
		
		sql="select t.xlatlongname,t.fieldvalue from psxlatitem t where t.fieldname=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, "SEX");
		Map<String,String> sexMap = new HashMap<String,String>();
		res = ps.executeQuery();
		while(res.next()){
			sexMap.put(res.getString("fieldvalue"), res.getString("xlatlongname"));
		}
		res.close();
		log.debug("sexMap size:"+sexMap.size());
		
		
		Map<String,String> marMap = new HashMap<String,String>();
		ps.setString(1, "MAR_STATUS");
		res = ps.executeQuery();
		while(res.next()){
			marMap.put(res.getString("fieldvalue"), res.getString("xlatlongname"));
		}
		res.close();
		log.debug("marMap size:"+marMap.size());
		

		Map<String,String> eduMap = new HashMap<String,String>();
		ps.setString(1, "HIGHEST_EDUC_LVL");
		res = ps.executeQuery();
		while(res.next()){
			eduMap.put(res.getString("fieldvalue"), res.getString("xlatlongname"));
		}
		res.close();
		ps.close();
		log.debug("eduMap size:"+eduMap.size());
		
		
		sql="select t.emplid 工号,"
			+" t.name 姓名,"
			+" to_char(t.birthdate, 'YYYYMM') as 出生日期,"
			+" t.sex,"
			+" t.mar_status,"
			+" t.highest_educ_lvl,"
			+" t.birthstate,"
			+" t.ft_student"
			+" from ps_personal_data t";
		ps = conn.prepareStatement(sql);
		res = ps.executeQuery();
		while(res.next()){
			Employee emp = new Employee();
			emp.setEmployeeId(res.getString("工号"));
			emp.setName(res.getString("姓名"));
			emp.setBirthDate(res.getString("出生日期"));

			String key = res.getString("sex");
			String value = null;
			if(null==(value=sexMap.get(key))){
				value=NOT_DEFINED;
			}
			emp.setSex(value);
			
			key=res.getString("mar_status");
			if(null==(value=marMap.get(key)))
				value=NOT_DEFINED;
			emp.setMaritalStatus(value);
			
			key=res.getString("highest_educ_lvl");
			if(null==(value=eduMap.get(key)))
				value=NOT_DEFINED;
			emp.setEducationLevel(value);
			
			key=res.getString("birthstate");
			if(null==(value=hukouMap.get(key)))
				value=NOT_DEFINED;
			emp.setRegisteredResidence(value);
			
			key=res.getString("ft_student");//入司角色
			if(key.equals("Y")){
				emp.setJoinRole("应届生");
			}else if(key.equals("N"))
				emp.setJoinRole("非应届生");
			else
				emp.setJoinRole(NOT_DEFINED);
			
			
			JobInfo job = null;
			if((job=jobMap.get(emp.getEmployeeId()))==null){
				job = new JobInfo();
			}
			
			emp.setCompany(job.company);
			emp.setDepartment(job.dept);
			emp.setPosition(job.jc.postion);
			emp.setPositionLevel(job.jc.postionLvl);
			emp.setPositionGrade(job.posngrade);
			emp.setPostionType(job.jc.postionType);
			emp.setIsBackbone(job.keyJob);
			emp.setJoinDate(job.jd.joinDate);
			emp.setJoinSource(job.jd.joinSource);
			emp.setStatus(job.inService);			
//			if(emp.employeeId.equals("000912")){
//				log.debug(emp.employeeId+" "+emp.name+" "+emp.company+" "+emp.department+" "+emp.position
//						+" "+emp.positionLevel+" "+ emp.positionGrade+" "+ emp.postionType+" "+emp.isBackbone
//						+" "+emp.birthDate.toLocaleString()+" "+emp.educationLevel+" "+emp.sex+" "+emp.maritalStatus
//						+" "+emp.joinTime.toLocaleString()+" "+emp.joinRole+" "+emp.joinSource+" "+emp.registeredResidence
//						+" "+emp.status);
//				}
			repo.addEmployee(emp);
		}		
		res.close();
		ps.close();
		log.debug("repo size:"+repo.getSize());
		
		log.debug("cost time:"+(System.currentTimeMillis() - l1));
		conn.close();
		 
	}

}
