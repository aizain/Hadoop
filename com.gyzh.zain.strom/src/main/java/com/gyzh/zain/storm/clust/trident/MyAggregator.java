package com.gyzh.zain.storm.clust.trident;

import org.apache.storm.tuple.Values;
import org.apache.storm.trident.operation.BaseAggregator;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;

public class MyAggregator extends BaseAggregator<Integer> {
	private int count = 0;
	
	@Override
	public Integer init(Object batchId, TridentCollector collector) {
		this.count = 0;
		return 0;
	}

	@Override
	public void aggregate(Integer val, TridentTuple tuple, TridentCollector collector) {
		count++;
	}

	@Override
	public void complete(Integer val, TridentCollector collector) {
		collector.emit(new Values(count));
	}

}
