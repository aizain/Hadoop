package com.gyzh.zain.strom;

import java.util.Map;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class SentenceSpout extends BaseRichSpout {
    private static String [] sentences = {
            "i am so shuai",
            "do you like me",
            "are you sure you do not like me",
            "yes i am sure"
        };
        
        private SpoutOutputCollector collector = null;
        
        /**
                                来自ISpout接口
                                在初始化时被调用，来执行初始化操作
            conf 配置信息
            context 任务信息
            collector 发射数据用的组件，本身线程安全
         */
        @Override
        public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
            this.collector = collector;
        }

        /**
                                来自ISpout
            Storm会不停的来调用此方法 要求数据 我们可以在此方法中发射数据
                                阻塞可能会影响storm的工作，如果没有数据要发送，也不要阻塞此方法，只需要简单的返回即可。
                                由于storm会不停调用此方法，所以即使没有数据要返回，也最好睡上很短的一段时间，让cpu不至于被过多的占用
         */
        private int index = 0;
        @Override
        public void nextTuple() {
            if(index >= sentences.length)return;
            collector.emit(new Values(sentences[index]),index);
            index++;
            //index = index >= sentences.length-1 ? 0 : index+1;
        }

        /**
                                来自IComponent
                                用来声明当前Spout要输出什么样的Stream
            declarer 用来声明输出字段的对象
         */
        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("sentence"));
        }


        @Override
        public void fail(Object msgId) {
            System.out.println("------------消息处理失败："+msgId+"--重发数据！");
            collector.emit(new Values(sentences[(Integer)msgId]),msgId);
        }
        
        @Override
        public void ack(Object msgId) {
            System.out.println("------------消息处理成功："+msgId);
        }
}
