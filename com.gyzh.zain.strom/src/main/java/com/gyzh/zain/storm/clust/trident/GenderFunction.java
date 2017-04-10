package com.gyzh.zain.storm.clust.trident;

import org.apache.storm.tuple.Values;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;

public class GenderFunction extends BaseFunction{

	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		String name = tuple.getStringByField("name");
		collector.emit(new Values("xiaohua".equals(name) ? "female" : "male"));
	}

}
