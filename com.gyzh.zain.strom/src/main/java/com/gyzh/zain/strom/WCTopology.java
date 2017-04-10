package com.gyzh.zain.strom;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class WCTopology {
	private static final String SENTENCE_SPOUT_ID = "sentence-spout";
	private static final String SPLIT_BOLT_ID = "split-bolt";
	private static final String COUNT_BOLT_ID = "count-bolt";
	private static final String REPORT_BOLT_ID = "report-bolt";
	private static final String TOPOLOGY_NAME = "word-count-topology";

	public static void main(String[] args) throws Exception {
        //--实例化Spout和Bolt
        SentenceSpout spout = new SentenceSpout();
        SplitSentenceBolt splitBolt = new SplitSentenceBolt();
        WordCountBolt countBolt = new WordCountBolt();
        ReportBolt reportBolt = new ReportBolt();
        
        //--创建TopologyBuilder类实例
        TopologyBuilder builder = new TopologyBuilder();
        
        //--注册spout
        builder.setSpout(SENTENCE_SPOUT_ID, spout);
        //--注册splitBolt
        builder.setBolt(SPLIT_BOLT_ID, splitBolt).shuffleGrouping(SENTENCE_SPOUT_ID);
        //--注册countBolt
        builder.setBolt(COUNT_BOLT_ID, countBolt).fieldsGrouping(SPLIT_BOLT_ID,new Fields("word"));
        //--注册reportBolt
        builder.setBolt(REPORT_BOLT_ID, reportBolt).globalGrouping(COUNT_BOLT_ID);
    
        //--生产拓扑
        StormTopology topology = builder.createTopology();
        
        //--创建配置对象
        Config conf = new Config();
        
        //--将topology交给集群去运行
        //----提交到本地模拟的集群中进行测试
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(TOPOLOGY_NAME, conf, topology);
        //--10秒钟后停止程序
        Thread.sleep(1000 * 1000);
        cluster.killTopology(TOPOLOGY_NAME);
        cluster.shutdown();
        
        //----真正提交到Storm集群
        //StormSubmitter.submitTopology(TOPOLOGY_NAME, conf, topology);
    
	}
}
