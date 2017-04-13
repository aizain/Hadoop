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

public class NewCustBolt extends BaseRichBolt {
	private OutputCollector collecotr = null;
	
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collecotr = collector;
	}

	@Override
	public void execute(Tuple input) {
		String uvid = input.getStringByField("uvid");
		List<NetFlowInfo> list = HBaseDao.find("^\\d*_\\d*_\\d*_"+uvid+"_.*$");
		int newcust = list.size() == 0 ? 1 : 0;
		List<Object> values = input.getValues();
		values.add(newcust);
		collecotr.emit(input,values);
		collecotr.ack(input);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("urlname", "uvid","sid","scount","stime","cip","pv","uv","vv","newip","newcust"));
	}

}
