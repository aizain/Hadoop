package com.gyzh.zain.storm.clust.trident;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;
import org.apache.storm.trident.Stream;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.builtin.Count;
import org.apache.storm.trident.testing.FixedBatchSpout;

/**
 * 测试trident框架
 * 
 * @author zain
 * 17/04/10
 */
public class TridentDemo1 {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        // --创建spout
        // 自己编写类来开发Spout
        // SentenceSpout spout = new SentenceSpout();
        // 利用便捷工具创建用来做测试的Spout，失败会重新发送
        FixedBatchSpout spout = new FixedBatchSpout(
                new Fields("name","sentence"), 
                7, // 每批发送的数据数量
                new Values("xiaoming","i am so shuai"),
                new Values("xiaoming","do you like me"),
                new Values("xiaohua","i do not like you"),
                new Values("xiaohua","you look like fengjie"),
                new Values("xiaoming","are you sure you do not like me"),
                new Values("xiaohua","yes i am"),
                new Values("xiaoming","ok i am sure"));
        // 是否循环输出
        spout.setCycle(false);
        
        // --创建Topology
        TridentTopology tridentTopology = new TridentTopology();
        
        // --设置Spout
        Stream stream = tridentTopology.newStream("asdf", spout);
        
        // --设置过滤器过滤小花说的话
        // stream = stream.each(new Fields("name"), new XiaohuaFilter());
        
        // --设置Function增加性别属性
        stream = stream.each(new Fields("name"), new GenderFunction(), new Fields("gender"));
        
        // --分区聚合 统计每个分区中tuple的数量
        // stream = stream.partitionAggregate(new Fields("name"), new MyCombinerAggerator(), new Fields("tCount"));
        // stream = stream.partitionAggregate(new Fields("name"), new MyReducerAggregator(), new Fields("tCount"));
        // stream = stream.partitionAggregate(new Fields("name"),new MyAggregator(),new Fields("tCount"));
        // stream = stream.partitionAggregate(new Fields(), new Count(), new Fields("tCount"));
        
        // --分区聚合 统计xiaoming xiaohua各说了几句话
        // stream = stream.partitionAggregate(new Fields("name"), new SentenceCountAggreator(), new Fields("name","count"));
        
        // --投影操作，只保留name属性
        stream = stream.project(new Fields("name"));
        
        // --设置过滤器,打印属性
        stream = stream.each(stream.getOutputFields(), new PrintFilter());
        
        
        
        //--将topology提交到集群中运行
        Config conf = new Config();
        LocalCluster cluster = new LocalCluster();
        StormTopology sTopology = tridentTopology.build();
        cluster.submitTopology("MyTridentTopology", conf, sTopology);
        
        //--运行10秒钟后杀死Topology关闭集群
        Utils.sleep(1000 * 10);
        cluster.killTopology("MyTridentTopology");
        cluster.shutdown();
                
    }
}
