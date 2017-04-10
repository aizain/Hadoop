package com.gyzh.zain.storm.clust.trident;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.storm.tuple.Values;
import org.apache.storm.trident.operation.BaseAggregator;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;

public class SentenceCountAggreator extends BaseAggregator<Map<String,Integer>> {

    /**
     * 初始化，创建一个Map用作统计
     */
    @Override
    public Map<String, Integer> init(Object batchId, TridentCollector collector) {
        return new ConcurrentHashMap<String, Integer>();
    }

    /**
        每个tuple都会被调用一次此方法
        获取tuple中的名字
        存入map统计出现次数
     */
    @Override
    public void aggregate(Map<String, Integer> val, TridentTuple tuple, TridentCollector collector) {
        String name = tuple.getStringByField("name");
        val.put(name, val.containsKey(name) ? val.get(name) + 1 : 1);
    }

    /**
        所有tuple处理完调用此方法
        将map中的结果输出为流
     */
    @Override
    public void complete(Map<String, Integer> val, TridentCollector collector) {
        for(Map.Entry<String, Integer>entry : val.entrySet()){
            String name = entry.getKey();
            Integer count = entry.getValue();
            
            collector.emit(new Values(name,count));
        }
    }


}