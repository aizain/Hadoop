package com.gyzh.zain.storm.clust.trident;

import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

public class GenderSpout extends BaseRichSpout{
	private Values [] values = {
			new Values("xiaoming","male"),
			new Values("xiaohua","female"),
	};
	
	private SpoutOutputCollector collector = null;
	
	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
	}

	private int index = 0;
	@Override
	public void nextTuple() {
		if(index > values.length-1)return;
		collector.emit(values[index]);
		index++;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("name","gender"));
	}
	

}
