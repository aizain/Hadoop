package com.gyzh.zain.storm.clust.trident.mergejoin;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.testing.OpaqueMemoryTransactionalSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import com.gyzh.zain.storm.clust.trident.GenderSpout;
import com.gyzh.zain.storm.clust.trident.PrintFilter;
import com.gyzh.zain.storm.clust.trident.SentenceSpout;

import org.apache.storm.trident.Stream;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.testing.FixedBatchSpout;

public class JoinDemo {
    public static void main(String[] args) {
        SentenceSpout spout1 = new SentenceSpout();
        GenderSpout spout2 = new GenderSpout();
        
        //--创建Topology
        TridentTopology tridentTopology = new TridentTopology();
        
        Stream s1 = tridentTopology.newStream("s1", spout1);
        Stream s2 = tridentTopology.newStream("s2", spout2);
        
        // 选择两个stream的关键fields进行合并
        Stream s = tridentTopology.join(
                s1, 
                new Fields("name"), 
                s2,
                new Fields("name"),
                new Fields("name","sentence","gender"));
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

