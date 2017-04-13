package com.gyzh.zain.storm.netflow.more;

import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

public class TimeSpout extends BaseRichSpout{
	private SpoutOutputCollector collector = null;
	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
	}

	long begintime=System.currentTimeMillis();
	@Override
	public void nextTuple() {
		long nowtime = System.currentTimeMillis();
		if(nowtime - begintime >= 1000 * 3600){
			Object nowtiem;
			collector.emit(new Values(nowtime));
			begintime = nowtime;
		}else{
			return;
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("signal"));
	}

}
