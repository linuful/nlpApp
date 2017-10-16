package com.transfar.es;

import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.settings.Settings;


public class ESNodeClient {
	private  NodeClient client=null;
	public void open(){
		Settings setting=Settings.builder().build();
		client = new NodeClient(setting, null);
	}

}
