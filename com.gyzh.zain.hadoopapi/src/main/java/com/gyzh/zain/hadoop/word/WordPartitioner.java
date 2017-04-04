package com.gyzh.zain.hadoop.word;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * 自定义分区
 * 
 * 第一个泛型，Mapper输出的key的类型
 * 第二个泛型，Mapper输出的value的类型
 * 
 * @author zain
 * 17/04/04
 */
public class WordPartitioner extends Partitioner<Text, IntWritable> {

    /**
     * 
     * @param key Mapper输出的key
     * @param value Mapper输出的value
     * @param numPartitions
     * @return 
     */
    @Override
    public int getPartition(Text key, IntWritable value, int numPartitions) {
        // Hadoop默认的分区算法
         return key.hashCode() & Integer.MAX_VALUE % numPartitions;
        
        // 测试分区
//        if (key.equals("1") || key.equals("2")) {
//            return 0;
//        } 
//        if (key.equals("3")) {
//            return 1;
//        } 
//        return 2;
    }

}
