package com.gyzh.zain.storm.clust.trident;

import java.util.Iterator;
import java.util.Map;

import org.apache.storm.tuple.Fields;
import org.apache.storm.trident.operation.BaseFilter;
import org.apache.storm.trident.operation.TridentOperationContext;
import org.apache.storm.trident.tuple.TridentTuple;

public class PrintFilter extends BaseFilter{
	private String flag = "";
	
	public PrintFilter() {
	}
	
	public PrintFilter(String flag) {
		this.flag = flag;
	}
	
	private TridentOperationContext context = null;
	@Override
	public void prepare(Map conf, TridentOperationContext context) {
		this.context = context;
	}
	
	@Override
	public boolean isKeep(TridentTuple tuple) {
		Fields fields = tuple.getFields();
		Iterator<String> it = fields.iterator();
		while(it.hasNext()){
			String key = it.next();
			Object value = tuple.getValueByField(key);
			System.out.println("---"+flag+"--partition_id:"+context.getPartitionIndex()+"-------"+key+":"+value+"---");
		}
		return true;
	}


}
