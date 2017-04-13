package com.gyzh.zain.storm.netflow;

import java.util.UUID;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaConfig;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;

public class NetFlowDriver {
    public static void main(String[] args) throws Exception {
        //--创建TopologyBuilder类实例
        TopologyBuilder builder = new TopologyBuilder();
        
        //--创建spout从kafka中消费数据 转换为tuple发送
        BrokerHosts hosts = new ZkHosts("192.168.242.101:2181,192.168.242.102:2181,192.168.242.103:2181");
        SpoutConfig sconf = new SpoutConfig(hosts, "netflow", "/netflow",UUID.randomUUID().toString());
        sconf.scheme = new SchemeAsMultiScheme(new StringScheme());
        KafkaSpout spout = new KafkaSpout(sconf);
        PrintBolt printBolt = new PrintBolt();
        
        //设置Spout从Kafka消费数据
        builder.setSpout("KafkaSpout", spout);
        //整理数据
        builder.setBolt("ClearBolt", new ClearBolt()).shuffleGrouping("KafkaSpout");
        //计算PV
        builder.setBolt("PvBolt", new PvBolt()).shuffleGrouping("ClearBolt");
        //计算UV
        builder.setBolt("UvBolt", new UvBolt()).shuffleGrouping("PvBolt");
        
        builder.setBolt("PrintBolt", printBolt).shuffleGrouping("UvBolt");
        
        //--生产拓扑
        StormTopology topology = builder.createTopology();
        //--创建配置对象
        Config conf = new Config();
        //--将topology交给集群去运行
        //----提交到本地模拟的集群中进行测试
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("NetFlowTopology", conf, topology);
        //--10秒钟后停止程序
        Thread.sleep(1000 * 1000);
        cluster.killTopology("NetFlowTopology");
        cluster.shutdown(); 
    }
}
