package com.transfar.app;

import java.io.IOException;
import java.net.InetAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.transfar.hr.config.Config;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	Config.getInstance();
        Logger log = LogManager.getLogger();
        Settings settings = Settings.builder().put("cluster.name", "mycluster").build();//默认elasticsearch
        @SuppressWarnings({ "unchecked"})
		TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

        log.info(testJson());

       
        client.close();        
        log.info("run success.");
    }
    
    public static String testJson() throws IOException{
        XContentBuilder builder = XContentFactory
				.jsonBuilder()
				.startObject()
				.field("user", 
						XContentFactory
						.jsonBuilder()
						.startObject()
						.field("linianfu", "Samsung")
						.endObject().string())
				.endObject();
        return builder.string();
    }
    
    
   
}
