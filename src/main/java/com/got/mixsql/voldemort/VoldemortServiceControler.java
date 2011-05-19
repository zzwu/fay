package com.got.mixsql.voldemort;

import java.util.List;

import com.got.mixsql.utils.FileUtils;

import voldemort.server.VoldemortConfig;
import voldemort.server.VoldemortServer;
import voldemort.store.StoreDefinition;
import voldemort.xml.StoreDefinitionsMapper;

public class VoldemortServiceControler {
	
	private VoldemortServer voldemortServer;

	public VoldemortServiceControler(List<StoreDefinition> stores) {
		VoldemortConfig config = VoldemortConfig.loadFromVoldemortHome(getClass().getResource("/").getPath());
		FileUtils.saveToFile(config.getVoldemortHome() + "/config/stores.xml", new StoreDefinitionsMapper().writeStoreList(stores), "UTF-8");
		voldemortServer = new VoldemortServer(config);
	}
	
	/**
	 * 开启voldemort服务。
	 */
	public void run() {
		voldemortServer.start();
	}
	
	/**
	 * 停止voldemort服务。
	 */
	public void stop() {
		voldemortServer.stop();
	}
	

}
