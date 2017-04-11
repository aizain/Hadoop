package com.gyzh.zain.storm.clust.trident.state;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.storm.trident.state.State;

public class MyState implements State ,Serializable {
	
	private Map<String,List<String>> map = new ConcurrentHashMap<>();
	
	public void put(String key,String value){
		if(map.containsKey(key)){
			map.get(key).add(value);
		}else{
			List<String> list = new ArrayList();
			list.add(value);
			map.put(key, list);
		}
	}
	
	public List<String> get(String key){
		return map.get(key);
	}
	
	@Override
	public void beginCommit(Long txid) {

	}

	@Override
	public void commit(Long txid) {

	}

}
