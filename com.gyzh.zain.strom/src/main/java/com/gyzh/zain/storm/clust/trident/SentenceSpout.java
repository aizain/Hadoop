package com.gyzh.zain.storm.clust.trident;

import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

/**
 * 自定义开发的spout
 * 
 * @author zain
 * 17/04/10
 */
public class SentenceSpout extends BaseRichSpout{
	private Values [] values = {
			new Values("xiaoming","i am so shuai"),
			new Values("xiaoming","do you like me"),
			new Values("xiaohua","i do not like you"),
			new Values("xiaohua","you look like fengjie"),
			new Values("xiaoming","are you sure you do not like me"),
			new Values("xiaohua","yes i am"),
			new Values("xiaoming","ok i am sure")
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
		declarer.declare(new Fields("name","sentence"));
	}
	

}
