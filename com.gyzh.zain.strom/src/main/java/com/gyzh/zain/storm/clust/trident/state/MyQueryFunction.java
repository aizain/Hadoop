package com.gyzh.zain.storm.clust.trident.state;

import java.util.ArrayList;
import java.util.List;

import org.apache.storm.tuple.Values;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.state.BaseQueryFunction;
import org.apache.storm.trident.tuple.TridentTuple;

/**
 * 查询方法的实现
 * @author zain
 * 17/04/11
 */
public class MyQueryFunction extends BaseQueryFunction<MyState, List<String>> {
    
    /**
     * 真正的查询方法
     */
	@Override
	public List<List<String>> batchRetrieve(MyState state, List<TridentTuple> tuples) {
		List<List<String>> retList = new ArrayList<List<String>>();
		for(TridentTuple tuple : tuples){
			String name = tuple.getStringByField("name");
			List<String> list = state.get(name);
			retList.add(list);
		}
		return retList;
	}
	
	/**
	 * 查询结束后的发送
	 */
	@Override
	public void execute(TridentTuple tuple, List<String> result, TridentCollector collector) {
		String name = tuple.getStringByField("name");
		for(String sentence : result){
			collector.emit(new Values(name,sentence));
		}
		
	}
	
}
