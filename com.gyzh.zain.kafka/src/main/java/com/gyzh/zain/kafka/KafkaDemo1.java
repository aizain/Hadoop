package com.gyzh.zain.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.javaapi.producer.Producer;
import kafka.message.MessageAndMetadata;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class KafkaDemo1 {
	public void put(){
		Properties props = new Properties();
		props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("metadata.broker.list", "192.168.242.101:9092");
        
        Producer<Integer, String> producer = new Producer<>(new ProducerConfig(props));
        producer.send(new KeyedMessage<Integer, String>("park", "from java~~~"));
        
        producer.close();
	}
	
	public void get(){
        //声明连接属性
        Properties properties = new Properties();  
        properties.put("zookeeper.connect", "192.168.242.101:2181,192.168.242.102:2181,192.168.242.103:2181");//声明zk  
        properties.put("group.id", "g_1");// 必须要使用别的组名称， 如果生产者和消费者都在同一组，则不能访问同一组内的topic数据  
        properties.put("auto.offset.reset", "smallest");
        
        //连接kafka
        ConsumerConnector consumer = Consumer.createJavaConsumerConnector(
                new ConsumerConfig(properties));

        //消费数据
        Map<String, Integer> confMap = new HashMap<>();
        confMap.put("park", 1);
        Map<String, List<KafkaStream<byte[], byte[]>>> ms 
                = consumer.createMessageStreams(confMap);
        KafkaStream<byte[], byte[]> ks = ms.get("park").get(0);
        ConsumerIterator<byte[], byte[]> it = ks.iterator();
        while(it.hasNext()){
            MessageAndMetadata<byte[], byte[]> next = it.next();
            byte[] message = next.message();
            String str = new String(message);
            System.out.println(str);
        }
        
        //断开连接
        consumer.shutdown();
    }
}
