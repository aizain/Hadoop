package com.gyzh.zain.storm.netflow.more;

import java.util.List;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

public class VvBolt extends BaseRichBolt implements IRichBolt {
	
	private OutputCollector collector = null;
	
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		int count = input.getIntegerByField("scount");
		int vv = count == 0 ? 1 : 0;
		List<Object> values = input.getValues();
		values.add(vv);
		collector.emit(input,values);
		collector.ack(input);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("urlname", "uvid","sid","scount","stime","cip","pv","uv","vv"));
	}

}
