package com.gyzh.zain.storm.clust.trident.mergejoin;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.base.BaseTransactionalSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import com.gyzh.zain.storm.clust.trident.PrintFilter;

import org.apache.storm.trident.Stream;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.testing.FixedBatchSpout;

public class MergeDemo {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        FixedBatchSpout spout1 = new FixedBatchSpout(
                new Fields("name","sentence"), 
                2, 
                new Values("xiaoming","i am so shuai"),
                new Values("xiaoming","do you like me"));
        spout1.setCycle(true);
        FixedBatchSpout spout2 = new FixedBatchSpout(
                new Fields("name","sentence"), 
                3, 
                new Values("xiaoming2","i am so shuai2"),
                new Values("xiaoming2","i am so shuai2"),
                new Values("xiaoming2","do you like me2"));
        spout2.setCycle(true);
        //--创建Topology
        TridentTopology tridentTopology = new TridentTopology();
        
        Stream s1 = tridentTopology.newStream("s1", spout1);
        Stream s2 = tridentTopology.newStream("s2", spout2);
        
        // 合并时要保证两个fields一致
        Stream s = tridentTopology.merge(s1,s2);
        
        s.each(s.getOutputFields(), new PrintFilter());
        
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
