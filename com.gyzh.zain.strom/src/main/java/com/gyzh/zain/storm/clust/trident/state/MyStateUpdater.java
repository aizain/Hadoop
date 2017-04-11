package com.gyzh.zain.storm.clust.trident.state;

import java.util.List;

import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.state.BaseStateUpdater;
import org.apache.storm.trident.tuple.TridentTuple;

/**
 * 对于数据的更新操作
 * @author zain
 * 17/04/11
 */
public class MyStateUpdater extends BaseStateUpdater<MyState> {

	@Override
	public void updateState(MyState state, List<TridentTuple> tuples, TridentCollector collector) {
		for(TridentTuple tuple : tuples){
			String name = tuple.getStringByField("name");
			String sentence = tuple.getStringByField("sentence");
			
			state.put(name, sentence);
		}
	}

}
