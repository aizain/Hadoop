package com.gyzh.zain.storm.clust.trident;

import org.apache.storm.trident.operation.BaseFilter;
import org.apache.storm.trident.tuple.TridentTuple;

public class XiaohuaFilter extends BaseFilter {

	@Override
	public boolean isKeep(TridentTuple tuple) {
		String name = tuple.getStringByField("name");
		return !"xiaohua".equals(name);
	}

}
