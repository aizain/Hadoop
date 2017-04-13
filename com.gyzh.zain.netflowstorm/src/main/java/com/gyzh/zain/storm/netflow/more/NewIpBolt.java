package com.gyzh.zain.storm.netflow.more;

import java.util.List;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

import com.gyzh.zain.hbase.dao.HBaseDao;
import com.gyzh.zain.hbase.dao.NetFlowInfo;
import com.gyzh.zain.utils.DateUtils;

public class NewIpBolt extends BaseRichBolt {
	private OutputCollector collector = null;
	
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		long time = input.getLongByField("stime");
		String dateStr = DateUtils.format(time);
		String cip = input.getStringByField("cip");
		List<NetFlowInfo> list = HBaseDao.find("^"+dateStr+"_\\d*_\\d*_\\d*_"+cip+"_\\d*$");
		int newip = list.size() == 0 ? 1 : 0;
		
		List<Object> values = input.getValues();
		values.add(newip);
		collector.emit(input,values);
		collector.ack(input);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("urlname", "uvid","sid","scount","stime","cip","pv","uv","vv","newip"));
	}

}
