package com.gyzh.zain.storm.netflow;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class ClearBolt extends BaseRichBolt{
	private OutputCollector collector = null;
	
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		String str = input.getStringByField("str");
		String attrs [] = str.split("\\|");
		String urlname = attrs[1];
		String uvid = attrs[13];
		String sid = attrs[14].split("_")[0];
		int scount = Integer.parseInt(attrs[14].split("_")[1]);
		long stime = Long.parseLong(attrs[14].split("_")[2]);
		String cip = attrs[15];
		collector.emit(input,new Values(urlname,uvid,sid,scount,stime,cip));
		collector.ack(input);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("urlname", "uvid","sid","scount","stime","cip"));
	}

}
