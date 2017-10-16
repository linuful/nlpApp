package com.transfar.hr.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.filechooser.FileSystemView;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

public class Config {
	private static Config instance=null;
	private Properties properties = null;
	private Logger log = null;

	public static synchronized Config getInstance(){ 
		if(instance==null){ 
		instance=new Config(); 
			} 
		return instance;
	}
	
	public Properties getProperties(){
		return this.properties;
	}
	
	public static  String  getDesktopPath(){
		FileSystemView fsv = FileSystemView.getFileSystemView();
		String desktop = fsv.getHomeDirectory().getPath();
		return desktop;
		
	}

	
	private Config(){
		
		properties = new Properties();
		
	    try {
			FileInputStream inputStream = new FileInputStream("resources/config.properties");
			properties.load(inputStream);
	        
			ConfigurationSource source = new ConfigurationSource(new FileInputStream("resources/log4j2.xml"));    
	        Configurator.initialize(null, source); 
	        
	        log = LogManager.getLogger(Config.class);
	
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
