package com.transfar.hr.etl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.transfar.hr.config.Config;
import com.transfar.hr.model.Interviewer;
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
import com.transfar.util.ExcelUtil;
import com.transfar.util.PoiMergedResult;


public class DayeeResumeParser {
	List<Interviewer> dayeeResumes = null;
	final static String BASEINFO = "个人基本信息";
	final static String[] BASEINFO_ATTR = {
			"姓名","性别","出生日期","国籍","民族","曾用名" //1-6
			,"籍贯","生源地","婚姻状况","最高学历","学位","研究方向","专业" //7 -13
			,"毕业时间","毕业院校","身份证号码","既往病史","入何党（团）派","入党（团）派时间" //14 - 19
			,"体重","身高","血型","右眼视力","左眼视力","照片","失业证号码","户口所在地" //20 -27
			,"户口性质","家庭地址","家庭电话","家庭地址邮编","现居住地","现居住地电话" //28-33
			,"现居住地邮编","档案所在地","档案所在地电话","档案所在地邮编","移动电话","电子邮箱" //34-39
			,"引荐人","期望薪资","个人其他要求"	//40 - 42
	};
	
	final static String LANGUAGEABILITIE = "语言能力";
	final static String[] LANGUAGEABILITIE_ATTR = {
			"语种","语言等级","取得时间"		
	};
	
	final static String PROFESSIONALSKILL = "专业技能";
	final static String[] PROFESSIONALSKILL_ATTR = {
			"任职资格（职称）","职称取得时间","技工类别及等级","技工类别及等级取得时间"
	};
	
	
	final static String EMERGENCYCONTACT = "紧急联系人";
	final static String[] EMERGENCYCONTACT_ATTR = {
		"姓名","居住地址","电话","邮编"
	};
	
	final static String FAMILY_RELATIONSHIPS = "家庭关系";
	final static String[] FAMILY_RELATIONSHIPS_ATTR = {
			"关系","姓名","出生年月","政治面貌","学历","工作单位","任职资格","职务"
	};
	
	
	final static String EDUCATIONEXPERIENCE = "教育经历";
	final static String[] EDUCATIONEXPERIENCE_ATTR = {
		"开始时间","结束时间","学校","学位","学历","专业","辅修专业"
	};
	
	
	final static String SELFINTRODUCE = "自我评价";
	final static String[] SELFINTRODUCE_ATTR = {
		"技能或特长","兴趣爱好","性格及自我评价"
	};
	
	final static String WORKEXPERIENCE = "工作经历";
	final static String[] WORKEXPERIENCE_ATTR = {
		"开始时间","结束时间","企业名称","所在部门","职位名称","担任最高职务时间"	//1-6
		,"工作描述","下属人数","主要业绩以及荣誉","月平均工资","直接上级姓名"		//7-11
		,"直接上级职务","企业规模","直接上级电话","企业性质","单位人数"		//12-16
		,"邮政编码","证明人及电话","主营产品","离职原因" 		//17-20
	};
	
	final static String TRAININGEXPERIENCE = "培训经历";
	final static String[] TRAININGEXPERIENCE_ATTR = {
			"开始时间","结束时间","培训机构","培训内容"
	};
	
	
	final static String PROJECTEXPERIENCE = "项目经验";
	final static String[] PROJECTEXPERIENCE_ATTR = {
			"开始时间","结束时间","项目名称","项目描述","项目职责"
	};
	
	//TODO add
	/*
	 * 应聘信息
	 * 面试轮次
	 * */
	Map<String,Integer> baseCodec = null;
	Map<String,Integer> langCodec = null;
	Map<String,Integer> profCodec = null;
	Map<String,Integer> conCodec = null;
	Map<String,Integer> faCodec = null;
	Map<String,Integer> eduCodec = null;
	Map<String,Integer> selfCodec = null;
	Map<String,Integer> workCodec = null;
	Map<String,Integer> trainCodec = null;
	Map<String,Integer> projCodec = null;
	
	
	public static Logger log = LogManager.getLogger(DayeeResumeParser.class);
	
	public DayeeResumeParser(){

		baseCodec = enCodec(BASEINFO_ATTR);
		langCodec = enCodec(LANGUAGEABILITIE_ATTR);
		profCodec = enCodec(PROFESSIONALSKILL_ATTR);
		conCodec = enCodec(EMERGENCYCONTACT_ATTR);
		faCodec = enCodec(FAMILY_RELATIONSHIPS_ATTR);
		eduCodec = enCodec(EDUCATIONEXPERIENCE_ATTR);
		selfCodec = enCodec(SELFINTRODUCE_ATTR);		
		workCodec = enCodec(WORKEXPERIENCE_ATTR);
		trainCodec = enCodec(TRAININGEXPERIENCE_ATTR);
		projCodec = enCodec(PROJECTEXPERIENCE_ATTR);
		
		dayeeResumes = new LinkedList<Interviewer>();
		//log=Logger.getLogger(this.getClass());
	}
	
	
	private Map<String,Integer> enCodec(String[] attr)
	{
		 Map<String,Integer> codec = new HashMap<String,Integer>();
		int index = 1;
		for(String key:attr){
			if(!codec.containsKey(key)){
				codec.put(key, index++);
			}			
		}
		if(codec.isEmpty())
			log.warn("property empty.");//System.out.println("property empty!");
		return codec;
	}
	
	/**
	 * 
	 * @param key
	 * @param type 1:base , 2:language, 3:professional skill,4:Emergency contact,5:family relationship
	 * 				6:education,7:self introduce,8:work experience,9:training,10:project
	 * @return	codec
	 */
	public Integer getCodec(String key,Integer type){
		
		switch(type){
		case 1:
			if(baseCodec.containsKey(key))
				return baseCodec.get(key);
			else{
				log.error("fail to get codec for key-type:[" + key + "," + type + "].");
			}
			break;
		case 2:
			if(langCodec.containsKey(key))
				return langCodec.get(key);
			else{
				log.error("fail to get codec for key-type:[" + key + "," + type + "].");
			}
			break;
		case 3:
			if(profCodec.containsKey(key))
				return profCodec.get(key);
			else{
				log.error("fail to get codec for key-type:[" + key + "," + type + "].");
			}
			break;
		case 4:
			if(conCodec.containsKey(key))
				return conCodec.get(key);
			else{
				log.error("fail to get codec for key-type:[" + key + "," + type + "].");
			}
			break;
		case 5:
			if(faCodec.containsKey(key))
				return faCodec.get(key);
			else{
				log.error("fail to get codec for key-type:[" + key + "," + type + "].");
			}
			break;
		case 6:
			if(eduCodec.containsKey(key))
				return eduCodec.get(key);
			else{
				log.error("fail to get codec for key-type:[" + key + "," + type + "].");
			}
			break;
		case 7:
			if(selfCodec.containsKey(key))
				return selfCodec.get(key);
			else{
				log.error("fail to get codec for key-type:[" + key + "," + type + "].");
			}
			break;
		case 8:
			if(workCodec.containsKey(key))
				return workCodec.get(key);
			else{
				log.error("fail to get codec for key-type:[" + key + "," + type + "].");
			}
			break;
		case 9:
			if(trainCodec.containsKey(key))
				return trainCodec.get(key);
			else{
				log.error("fail to get codec for key-type:[" + key + "," + type + "].");
			}
			break;
		case 10:
			if(projCodec.containsKey(key))
				return projCodec.get(key);
			else{
				log.error("fail to get codec for key-type:[" + key + "," + type + "].");
			}
			break;
		default:
			log.error("type:"+ type+" not exist.");
			break;
		}
		return null;
	}
	
	
	
	public Integer getBaseCodec(String key){
		if(baseCodec.containsKey(key))
			return baseCodec.get(key);
		else
		{
			log.error("fail to get codec for [" + key + "].");
			return null;
		}
	}
	public Integer getworkCodec(String key){
		if(workCodec.containsKey(key))
			return workCodec.get(key);
		else
		{
			log.error("fail to get codec for [" + key + "].");
			return null;
		}
	}
	
	public static void main(String[] args) throws IOException{
        Config.getInstance();
        System.out.println(System.getProperty("user.dir"));
		DayeeResumeParser dayee = new DayeeResumeParser();
		String excel = Config.getDesktopPath()+"/简历1.xlsx";

        dayee.parser(excel);
	}
	
	public Interviewer parser(String excelFile) throws IOException{
		
		Interviewer iv=new Interviewer();
		
		Workbook workbook = ExcelUtil.getWorkBook(excelFile);
		Integer sheetIdx = workbook.getNumberOfSheets();
		if(sheetIdx != 1){
			log.error("excel format wrong,sheet count:["+sheetIdx+"].");
			return null;
		}
		Sheet sheet = workbook.getSheetAt(0);
        int firstRowNum  = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();
        if(0!=firstRowNum || 4 != lastRowNum){
        	log.error("excel format wrong,row range:["+firstRowNum+"-"+lastRowNum+"].");
        	return null;
        }
        Row topicRow = sheet.getRow(0);
        Row propertyKeyRow = sheet.getRow(1);
        Row propertyDataRow = sheet.getRow(3);
        if(topicRow==null||propertyKeyRow==null||propertyDataRow==null){
        	log.error("excel format wrong,topicRow:["+topicRow+"],propertyRow:["+propertyKeyRow+"],dataRow:["+propertyDataRow+"].");
        	return null;
        }

        //personId
        Cell personIdCell = propertyDataRow.getCell(0);
        iv.setPersonId(ExcelUtil.getCellValue(personIdCell));
        //log.debug("personId:" + personIdCell);
        
        //topic data
        for(int idx=topicRow.getFirstCellNum(); idx<topicRow.getLastCellNum();idx++){
        	Cell topicCell = topicRow.getCell(idx);
        	String topic = ExcelUtil.getCellValue(topicCell);
        	PoiMergedResult ret = ExcelUtil.isMergedRegion(sheet,0,idx);
        	if(!ret.merged){
        		//log.debug("topic:["+topic+"]-(0,"+idx+") it not merge cell.");
        		continue;
        	}
        	if(topic.isEmpty())continue;
        	
        	switch(topic){
        	case BASEINFO:
        		Base b = new Base();
        		for(int idx1=ret.startCol;idx1<=ret.endCol;idx1++){
        			Cell propertyKeyCell = propertyKeyRow.getCell(idx1);
        			Cell propertyDataCell = propertyDataRow.getCell(idx1);
        			saveData(b,ExcelUtil.getCellValue(propertyKeyCell),ExcelUtil.getCellValue(propertyDataCell));
        			}
        		iv.setBase(b);
        		log.debug(iv.getPersonId()+" "+b.getName()+" "+b.getHighestEducationLevel());
        		break;
        	case PROFESSIONALSKILL:
        		Skill sk = new Skill();
           		for(int idx1=ret.startCol;idx1<=ret.endCol;idx1++){
        			Cell propertyKeyCell = propertyKeyRow.getCell(idx1);
        			Cell propertyDataCell = propertyDataRow.getCell(idx1);
        			saveData(sk,ExcelUtil.getCellValue(propertyKeyCell),ExcelUtil.getCellValue(propertyDataCell));
        			}
           		//log.debug(sk.titleAcquireTime);
        		iv.addSkills(sk);
        		break;
        	case EMERGENCYCONTACT:
        		EmergencyContact ec = new EmergencyContact();
           		for(int idx1=ret.startCol;idx1<=ret.endCol;idx1++){
        			Cell propertyKeyCell = propertyKeyRow.getCell(idx1);
        			Cell propertyDataCell = propertyDataRow.getCell(idx1);
        			saveData(ec,ExcelUtil.getCellValue(propertyKeyCell),ExcelUtil.getCellValue(propertyDataCell));
        			}
           		//log.debug(ec.name+" "+ec.residencePlace+" "+ec.telphone+" "+ec.zipCode);
           		iv.addEmergencyContacts(ec);
        		break;
        	case FAMILY_RELATIONSHIPS:
        		FamilyMember fa = new FamilyMember();
           		for(int idx1=ret.startCol;idx1<=ret.endCol;idx1++){
        			Cell propertyKeyCell = propertyKeyRow.getCell(idx1);
        			Cell propertyDataCell = propertyDataRow.getCell(idx1);
        			saveData(fa,ExcelUtil.getCellValue(propertyKeyCell),ExcelUtil.getCellValue(propertyDataCell));
        			}
           		//log.debug(fa.relationShips +fa.name+ fa.politics+fa.birthDay);
           		iv.addFamilyMember(fa);
        		break;
        	case SELFINTRODUCE:
        		SelfIntroduce si = new SelfIntroduce();
           		for(int idx1=ret.startCol;idx1<=ret.endCol;idx1++){
        			Cell propertyKeyCell = propertyKeyRow.getCell(idx1);
        			Cell propertyDataCell = propertyDataRow.getCell(idx1);
        			saveData(si,ExcelUtil.getCellValue(propertyKeyCell),ExcelUtil.getCellValue(propertyDataCell));
        			}
           		
           		iv.setSelfIntroduce(si);
           		//log.debug(iv.getSelfIntroduce().skills+iv.getSelfIntroduce().interests+"\n"+iv.getSelfIntroduce().selfReview);
        		break;
        	case TRAININGEXPERIENCE:
        		Training tr = new Training();
           		for(int idx1=ret.startCol;idx1<=ret.endCol;idx1++){
        			Cell propertyKeyCell = propertyKeyRow.getCell(idx1);
        			Cell propertyDataCell = propertyDataRow.getCell(idx1);
        			saveData(tr,ExcelUtil.getCellValue(propertyKeyCell),ExcelUtil.getCellValue(propertyDataCell));
        			}
           		//log.debug(tr.context+tr.institution);
           		iv.addTrainings(tr);
        		break;
        	case PROJECTEXPERIENCE:
        		Project proj = new Project();
           		for(int idx1=ret.startCol;idx1<=ret.endCol;idx1++){
        			Cell propertyKeyCell = propertyKeyRow.getCell(idx1);
        			Cell propertyDataCell = propertyDataRow.getCell(idx1);
        			saveData(proj,ExcelUtil.getCellValue(propertyKeyCell),ExcelUtil.getCellValue(propertyDataCell));
        			}
           		//log.debug(proj.describe+proj.responsibility);
           		iv.addProjects(proj);
        		break;
        	default:
        		if(topic.startsWith(LANGUAGEABILITIE)){
             		Language lang = new Language();
               		for(int idx1=ret.startCol;idx1<=ret.endCol;idx1++){
            			Cell propertyKeyCell = propertyKeyRow.getCell(idx1);
            			Cell propertyDataCell = propertyDataRow.getCell(idx1);
            			saveData(lang,ExcelUtil.getCellValue(propertyKeyCell),ExcelUtil.getCellValue(propertyDataCell));
            			}
               		//log.debug(lang.type+lang.grade+lang.acquireTime);
               		iv.addLanguages(lang);
        		}
        		else if(topic.startsWith(EDUCATIONEXPERIENCE)){
            		Education edu = new Education();
               		for(int idx1=ret.startCol;idx1<=ret.endCol;idx1++){
            			Cell propertyKeyCell = propertyKeyRow.getCell(idx1);
            			Cell propertyDataCell = propertyDataRow.getCell(idx1);
            			saveData(edu,ExcelUtil.getCellValue(propertyKeyCell),ExcelUtil.getCellValue(propertyDataCell));
            			}
               		//log.debug(edu.startTime+edu.endTime+edu.college+edu.degree+edu.level+edu.major);
               		iv.addEducations(edu);
        		}
        		else if(topic.startsWith(WORKEXPERIENCE)){
        			Experience exp = new Experience();
               		for(int idx1=ret.startCol;idx1<=ret.endCol;idx1++){
            			Cell propertyKeyCell = propertyKeyRow.getCell(idx1);
            			Cell propertyDataCell = propertyDataRow.getCell(idx1);
            			//System.out.println(POIExcel.getCellValue(propertyKeyCell));
            			saveData(exp,ExcelUtil.getCellValue(propertyKeyCell),ExcelUtil.getCellValue(propertyDataCell));
            			}
               		//log.debug(exp.employeeNumber+","+exp.company+","+exp.monthAveSalary);
               		iv.addExperiences(exp);
        		}
        		else{
        			//log.warn("topic:["+ topic+"] will not be saved.");
        		}

        		break;
        	}
        	//getCodec(topic,1);
        }		
		return iv;
	}
	
	private Boolean saveData(Base b,String propertyKey,String property){
		if(null==b){
			b = new Base();
		}
		Boolean ret = true;

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		switch(getCodec(propertyKey, 1)){
		case 1:
			b.setName(property);
			break;
		case 2:
			b.setSex(property);
			break;
		case 3:
			try{
				b.setBirthDate(format.parse(property));
			 	} 
			catch (ParseException e) {
				 Calendar calendar=Calendar.getInstance();
				 calendar.set(1900, 1,1);
				 b.setBirthDate(calendar.getTime());
				    log.warn(e.getMessage() + ", set birthday:190001.");
			   }
				//b.birthDate=property;
				break;
		case 4:
			b.setCountry(property);
			break;
		case 5:
			b.setNationality(property);
			break;
		case 6:
			b.setFormerName(property);
			break;
		case 7:
			b.setNativePlace(property);
			break;
		case 8:
			b.setBirthPlace(property);
			break;
		case 9:
			b.setMaritalStatus(property);
			break;
		case 10:
			b.setHighestEducationLevel(property);
			break;
		case 11:
			b.setEducationalDegree(property);
			break;
		case 12:
			b.setResearchDirection(property);
			break;
		case 13:
			b.setMajor(property);
			break;
		case 14:
			try{
				b.setGraduationTime(format.parse(property));
			 	} 
			catch (ParseException e) {
				Calendar calendar=Calendar.getInstance();
				calendar.set(1900, 1,1);
				b.setGraduationTime(calendar.getTime());
				log.warn(e.getMessage() + ", set graduationTime:190001.");
			   }
			break;
		case 15:
			b.setCollege(property);
			break;
		case 16:
			b.setIdentity(property);
			break;
		case 17:
			b.setMedicalHistory(property);
			break;
		case 18:
			b.setPartyOrGroup(property);
			break;
		case 19:
			try{
				b.setTimeOfJoinPOG(format.parse(property));
			 	} 
			catch (ParseException e) {
				Calendar calendar=Calendar.getInstance();
				calendar.set(1900,1,1);
				b.setTimeOfJoinPOG(calendar.getTime());
				log.warn(e.getMessage() + ", set timeOfJoinPOG:190001.");
			   }
			break;
		case 20:
			b.setWeight(property);
			break;
		case 21:
			b.setHeight(property);
			break;
		case 22:
			b.setBloodType(property);
			break;
		case 23:
			b.setLeftEyeSight(property);
			break;
		case 24:
			b.setRightEyeSight(property);
			break;
		case 25:
			b.setPicture(property);
			break;
		case 26:
			b.setUnemploymentCertificateId(property);
			break;
		case 27:
			b.setRegisteredResidence(property);
			break;
		case 28:
			b.setRegisteredType(property);
			break;
		case 29:
			b.setHomeAddress(property);
			break;
		case 30:
			b.setHomePhone(property);
			break;
		case 31:
			b.setHomeZipCode(property);
			break;
		case 32:
			b.setResidencePlace(property);
			break;
		case 33:
			b.setResidenceZipCode(property);
			break;
		case 34:
			b.setResidencePhone(property);
			break;
		case 35:
			b.setRecordsPlace(property);
			break;
		case 36:
			b.setRecordsPlacePhone(property);
			break;
		case 37:
			b.setRecordsPlaceZipCode(property);
			break;
		case 38:
			b.setTelphone(property);
			break;
		case 39:
			b.setEmail(property);
			break;
		case 40:
			b.setRecommender(property);
			break;
		case 41:
			b.setSalaryExpectation(property);
			break;
		case 42:
			b.setOtherRequirements(property);
			break;
		default:
			log.error("the codec index:["+getCodec(propertyKey, 1)+"]-base not exist.");
			ret=false;
			break;
		}
		return ret;
	}
	
	private Boolean saveData(Skill sk,String propertyKey,String property){
		if(null==sk){
			sk=new Skill();
		}
		Boolean ret = true;
		switch(this.getCodec(propertyKey,3)){
		case 1:
			sk.setTitle(property);
			break;
		case 2:
			sk.setTitleAcquireTime(property);
			break;
		case 3:
			sk.setTechnicalCategoriesAndGrades(property);
			break;
		case 4:
			sk.setTechnicalAcquireTime(property);
			break;
		default:
			log.error("the codec index:["+getCodec(propertyKey, 3)+"]-EmergencyContact not exist.");
			ret = false;
			break;
		}
		return ret;
	}
	
	private Boolean saveData(EmergencyContact ec,String propertyKey,String property){
		if(null==ec){
			ec=new EmergencyContact();
		}
		Boolean ret = true;
		switch(this.getCodec(propertyKey,4)){
		case 1:
			ec.setName(property);
			break;
		case 2:
			ec.setTelphone(property);
			break;
		case 3:
			ec.setResidencePlace(property);
			break;
		case 4:
			ec.setZipCode(property);
			break;
		default:
			log.error("the codec index:["+getCodec(propertyKey, 4)+"]-EmergencyContact not exist.");
			ret = false;
			break;
		}
		return ret;
	}
	
	private Boolean saveData(FamilyMember fa,String propertyKey,String property){
		if(null==fa){
			fa=new FamilyMember();
		}
		Boolean ret = true;
		switch(this.getCodec(propertyKey,5)){
		case 1:
			fa.setRelationShips(property);
			break;
		case 2:
			fa.setName(property);
			break;
		case 3:
			fa.setBirthDay(property);
			break;
		case 4:
			fa.setPolitics(property);
			break;
		case 5:
			fa.setEducation(property);
			break;
		case 6:
			fa.setWorkUnit(property);
			break;
		case 7:
			fa.setQualifications(property);
			break;
		case 8:
			fa.setPosition(property);
			break;
		default:
			log.error("the codec index:["+getCodec(propertyKey, 5)+"]-FamilyMember not exist.");
			ret = false;
			break;
		}
		return ret;
	}
	
	private Boolean saveData(SelfIntroduce ec,String propertyKey,String property){
		if(null==ec){
			ec=new SelfIntroduce();
		}
		Boolean ret = true;
		switch(this.getCodec(propertyKey,7)){
		case 1:
			ec.setSkills(property);
			break;
		case 2:
			ec.setInterests(property);
			break;
		case 3:
			ec.setSelfReview(property);
			break;
		default:
			log.error("the codec index:["+getCodec(propertyKey, 7)+"]-SelfIntroduce not exist.");
			ret = false;
			break;
		}
		return ret;
	}
	
	private Boolean saveData(Training ec,String propertyKey,String property){
		if(null==ec){
			ec=new Training();
		}
		Boolean ret = true;
		switch(this.getCodec(propertyKey,9)){
		case 1:
			ec.setStartTime(property);
			break;
		case 2:
			ec.setEndTime(property);
			break;
		case 3:
			ec.setInstitution(property);
			break;
		case 4:
			ec.setContext(property);
			break;
		default:
			log.error("the codec index:["+getCodec(propertyKey, 9)+"]-Training not exist.");
			ret = false;
			break;
		}
		return ret;
	}
	
	private Boolean saveData(Project ec,String propertyKey,String property){
		if(null==ec){
			ec=new Project();
		}
		Boolean ret = true;
		switch(this.getCodec(propertyKey,10)){
		case 1:
			ec.setStartTime(property);
			break;
		case 2:
			ec.setEndTime(property);
			break;
		case 3:
			ec.setName(property);
			break;
		case 4:
			ec.setDescribe(property);
			break;
		case 5:
			ec.setResponsibility(property);
			break;
		default:
			log.error("the codec index:["+getCodec(propertyKey, 10)+"]-Project not exist.");
			ret = false;
			break;
		}
		return ret;
	}
	
	private Boolean saveData(Language ec,String propertyKey,String property){
		if(null==ec){
			ec=new Language();
		}
		Boolean ret = true;
		switch(this.getCodec(propertyKey,2)){
		case 1:
			ec.setType(property);
			break;
		case 2:
			ec.setGrade(property);
			break;
		case 3:
			ec.setAcquireTime(property);
			break;
		default:
			log.error("the codec index:["+getCodec(propertyKey, 2)+"]-Language not exist.");
			ret = false;
			break;
		}
		return ret;
	}
	
	
	private Boolean saveData(Education ec,String propertyKey,String property){
		if(null==ec){
			ec=new Education();
		}
		Boolean ret = true;
		switch(this.getCodec(propertyKey,6)){
		case 1:
			ec.setStartTime(property);
			break;
		case 2:
			ec.setEndTime(property);
			break;
		case 3:
			ec.setCollege(property);
			break;
		case 4:
			ec.setDegree(property);
			break;
		case 5:
			ec.setLevel(property);
			break;
		case 6:
			ec.setMajor(property);
			break;
		case 7:
			ec.setMinor(property);
			break;
		default:
			log.error("the codec index:["+getCodec(propertyKey, 6)+"]-Education not exist.");
			ret = false;
			break;
		}
		return ret;
	}
	
	private Boolean saveData(Experience ec,String propertyKey,String property){
		if(null==ec){
			ec=new Experience();
		}
		Boolean ret = true;
		switch(this.getCodec(propertyKey,8)){
		case 1:
			ec.setStartTime(property);
			break;
		case 2:
			ec.setEndTime(property);
			break;
		case 3:
			ec.setCompany(property);
			break;
		case 4:
			ec.setDepartment(property);
			break;
		case 5:
			ec.setPosition(property);
			break;
		case 6:
			ec.setHighestPosition(property);
			break;
		case 7:
			ec.setDescribe(property);
			break;
		case 8:
			ec.setSubordinatesNumber(property);
			break;
		case 9:
			ec.setAchievements(property);
			break;
		case 10:
			//if(property.isEmpty())
			//	ec.monthAveSalary=0.0;
			//else
			//	ec.monthAveSalary = Double.valueOf(property);
			ec.setMonthAveSalary(property);
			break;
		case 11:
			ec.setSupervisorName(property);
			break;
		case 12:
			ec.setSuperiorPostion(property);
			break;
		case 13:
			ec.setSuperiorTelphone(property);
			break;
		case 14:
			ec.setEnterpriseScale(property);
			break;
		case 15:
			ec.setEnterpriseType(property);
			break;
		case 16:
			ec.setEmployeeNumber(property);
			break;
		case 17:
			ec.setZipCode(property);
			break;
		case 18:
			ec.setReferencesAndTelephone(property);
			break;
		case 19:
			ec.setProducts(property);
			break;
		case 20:
			ec.setResignReason(property);
			break;
		default:
			log.error("the codec index:["+getCodec(propertyKey, 8)+"]-Experience not exist.");
			ret = false;
			break;
		}
		return ret;
	}
}
