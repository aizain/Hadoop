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

public class TridentDemo3 {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        //--创建spout
        //自己编写类来开发Spout
//      SentenceSpout spout = new SentenceSpout();
        //利用便捷工具创建用来做测试的Spout
        FixedBatchSpout spout = new FixedBatchSpout(
                new Fields("name","sentence"), 
                3, 
                new Values("xiaoming","i am so shuai"),
                new Values("xiaoming","do you like me"),
                new Values("xiaohua","i do not like you"),
                new Values("xiaohua","you look like fengjie"),
                new Values("xiaoming","are you sure you do not like me"),
                new Values("xiaohua","yes i am"),
                new Values("xiaoming","ok i am sure"));
        spout.setCycle(false);
        
        //--创建Topology
        TridentTopology tridentTopology = new TridentTopology();
        
        //--设置Spout
        Stream stream = tridentTopology.newStream("asdf", spout);
        stream = stream.shuffle();
        stream = stream.each(stream.getOutputFields(), new PrintFilter("first"));
        stream = stream.parallelismHint(2);
        
        stream = stream.aggregate(stream.getOutputFields(), new Count(), new Fields("count"));
        stream.each(stream.getOutputFields(), new PrintFilter("second"));
        
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
