package com.gyzh.zain.strom;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class WordCountBolt extends BaseRichBolt{
	private Map<String,Integer> countMap = new ConcurrentHashMap<String, Integer>();
	private OutputCollector collector = null;
	
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		try {
			String word = input.getStringByField("word");
			countMap.put(word, countMap.containsKey(word) ? countMap.get(word)+1 : 1);
			collector.emit(input,new Values(word,countMap.get(word)));
			collector.ack(input);
		} catch (Exception e) {
			e.printStackTrace();
			collector.fail(input);
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word","count"));
	}

}
