package com.gyzh.zain.strom;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

public class ReportBolt extends BaseRichBolt {
    private Map<String,Integer> map = new ConcurrentHashMap<String, Integer>();
    private OutputCollector collector = null;
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
            this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        try {
            String word = input.getStringByField("word");
            int count = input.getIntegerByField("count");
            map.put(word, count);
            System.out.println("-----------数据发生变化：单词="+word+"---出现次数="+count);
            collector.ack(input);
        } catch (Exception e) {
            e.printStackTrace();
            collector.fail(input);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
    
    @Override
    public void cleanup() {
        System.out.println("-----------------------程序结束，最终统计结果-----------------------");
        for(Map.Entry<String, Integer> entry : map.entrySet()){
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
        System.out.println("-----------------------程序结束，最终统计结果-----------------------");
    }


}
