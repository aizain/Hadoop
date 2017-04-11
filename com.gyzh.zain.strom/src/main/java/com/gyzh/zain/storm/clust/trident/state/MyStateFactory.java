package com.gyzh.zain.storm.clust.trident.state;

import java.util.Map;

import org.apache.storm.task.IMetricsContext;
import org.apache.storm.trident.state.State;
import org.apache.storm.trident.state.StateFactory;

/**
 * 持久化工具的工厂
 * @author zain
 * 17/04/11
 */
public class MyStateFactory implements StateFactory {
	private MyState state = new MyState();
	@Override
	public State makeState(Map conf, IMetricsContext metrics, int partitionIndex, int numPartitions) {
		return state;
	}

}
