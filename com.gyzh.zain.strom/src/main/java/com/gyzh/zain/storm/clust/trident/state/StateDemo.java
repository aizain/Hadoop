package com.gyzh.zain.storm.clust.trident.state;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.base.BaseTransactionalSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import com.gyzh.zain.storm.clust.trident.PrintFilter;
import com.gyzh.zain.storm.clust.trident.SentenceSpout;

import org.apache.storm.trident.Stream;
import org.apache.storm.trident.TridentState;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.state.StateUpdater;
import org.apache.storm.trident.testing.FixedBatchSpout;

public class StateDemo {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        
        // --创建Topology
        TridentTopology tridentTopology = new TridentTopology();
        
        // --创建s1流
        SentenceSpout spout = new SentenceSpout();
        Stream s1 = tridentTopology.newStream("s1", spout);
        
        // --利用State将s1流中的数据持久化
        TridentState state = s1.partitionPersist(
                new MyStateFactory(), 
                new Fields("name","sentence"), 
                new MyStateUpdater());
        
        
        // --创建s2流
        FixedBatchSpout qspout = new FixedBatchSpout(
                new Fields("name"), 
                1, 
                new Values("xiaoming"),
                new Values("xiaohua"));
        Stream s2 = tridentTopology.newStream("qs", qspout);
        
        // --查询State获取信息
        s2 = s2.stateQuery(
                state, 
                new Fields("name"),
                new MyQueryFunction(), 
                new Fields("namex","sentencex"));
        s2.each(s2.getOutputFields(), new PrintFilter());
        
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
