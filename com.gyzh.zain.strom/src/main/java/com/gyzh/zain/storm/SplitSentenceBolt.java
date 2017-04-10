package com.gyzh.zain.storm;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class SplitSentenceBolt extends BaseRichBolt {
    private OutputCollector collector = null;
    /**
        继承自IBolt
        初始化的方法，在组件被初始化时调用
        conf 当前bolt的配置信息对象
        context 当前环境信息对象
        collector 对外输出tuple用的对象
     */
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    /**
        来自IBolt
        处理tuple用的 一次一个tuple
     */
    @Override
    public void execute(Tuple input) {
        try {
            String sentence = input.getStringByField("sentence");
            
//          if("are you sure you do not like me".equals(sentence)){
//              throw new RuntimeException("随便抛个异常~就是这么人性~~~");
//          }
            
            String[] words = sentence.split(" ");
            for(String word : words){
                collector.emit(input,new Values(word));
            }
            collector.ack(input);
        } catch (Exception e) {
            e.printStackTrace();
            collector.fail(input);
        }
    }
    
    /**
        来自IComponent
        用来声明当前Spout要输出什么样的Stream
        declarer 用来声明输出字段的对象
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }

}
