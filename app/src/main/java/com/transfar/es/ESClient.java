package com.transfar.es;


import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.bulk.byscroll.BulkByScrollResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.transfar.hr.config.Config;
import com.transfar.hr.model.Employee;


public class ESClient {
    protected static Logger log = LogManager.getLogger(ESClient.class);
    private TransportClient client;

    /**
     * 初始化
     */
    public void open() {
        try {
        	Settings settings = Settings.builder().put("cluster.name",Config.getInstance().getProperties().getProperty("es.cluster.name")).build();
            client=new PreBuiltTransportClient(settings);
            client.addTransportAddress(new InetSocketTransportAddress(
            		InetAddress.getByName(Config.getInstance().getProperties().getProperty("es.cluster.address")), 9300));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param emp
     */
    public void creatIndex(Employee emp) {
    	client.prepareIndex("employees","employee").setSource(generateXBuilder(emp)).execute();
    }
    
    public String searchByEmployeeId(String empId){
    	SearchRequestBuilder request = client.prepareSearch();
    	SearchResponse response=request.setIndices("employees")
    	        .setTypes("employee")
    	        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
    	        .setQuery(QueryBuilders.termQuery("工号", empId))                 // Query
    	        //.setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Filter
    	        //.setFrom(0).setSize(60).setExplain(true) 从第几个索引开始
    	        .get();
    	
		return response.toString();
    	
    }
    
    /**
     * 
     * @param empId
     * @return employee number be deleted
     */
    public long deleteIndex(String empId){
    	BulkByScrollResponse response =
    	DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
    		        .filter(QueryBuilders.matchQuery("工号",empId)) 
    		        .source("employees")                                  
    		        .get();                                             
    	return response.getDeleted();
    }

    private XContentBuilder generateXBuilder(Employee user) {
    	XContentBuilder builder=null;
        try {
        	builder=XContentFactory.jsonBuilder();
            builder.startObject().field("工号",user.getEmployeeId())
            .field("姓名", user.getName())
            .field("公司", user.getCompany())
            .field("部门", user.getDepartment())
            .field("职位", user.getPosition())
            .field("职层", user.getPositionLevel())
            .field("职级", user.getPositionGrade())
            .field("岗位类别", user.getPostionType())
            .field("核心骨干", user.getIsBackbone())
            .field("出生日期", user.getBirthdate())
            .field("学历", user.getEducationLevel())
            .field("性别", user.getSex())
            .field("婚姻状况", user.getMaritalStatus())
            .field("入司时间", user.getJoinDate())
            .field("入司角色", user.getJoinRole())
            .field("入职来源", user.getJoinSource())
            .field("户籍所在地", user.getRegisteredResidence())
            .field("当前状态", user.getStatus())
            .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder;
    }

    
    public void bulkProcess(Collection<Employee> emps) {
    	BulkRequestBuilder bulkRequest = client.prepareBulk();
    	for(Employee emp:emps){
    		IndexRequestBuilder req=client.prepareIndex("employees", "employee").setSource(generateXBuilder(emp));
    		bulkRequest.add(req);
    	}
    	BulkResponse bulkResponse = bulkRequest.get();//Short version of execute().actionGet()
    	if (bulkResponse.hasFailures()) {
    		log.warn(bulkResponse.buildFailureMessage()); 
    	}
    	
    }


    /**
     * 关闭客户端
     */
    public void close() {
        client.close();
    }
}
