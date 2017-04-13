package com.gyzh.zain.storm.netflow.more;

import java.util.Iterator;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

public class PrintBolt extends BaseRichBolt{
	private OutputCollector collector = null;
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		Fields fields = input.getFields();
		Iterator<String> it = fields.iterator();
		StringBuffer buffer = new StringBuffer();
		buffer.append("----");
		while(it.hasNext()){
			String key = it.next();
			Object value = input.getValueByField(key);
			buffer.append("-"+key+":"+value+"-");
		}
		buffer.append("----");
		
		System.out.println(buffer.toString());
		
		collector.ack(input);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		
	}
	
}
