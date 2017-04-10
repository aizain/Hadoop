package com.gyzh.zain.storm.clust.wc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

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
