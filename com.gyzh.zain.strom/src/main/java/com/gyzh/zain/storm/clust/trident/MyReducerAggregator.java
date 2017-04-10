package com.gyzh.zain.storm.clust.trident;

import org.apache.storm.trident.operation.ReducerAggregator;
import org.apache.storm.trident.tuple.TridentTuple;

public class MyReducerAggregator implements ReducerAggregator<Integer>{

	@Override
	public Integer init() {
		return 0;
	}

	@Override
	public Integer reduce(Integer curr, TridentTuple tuple) {
		return curr + 1;
	}

}
