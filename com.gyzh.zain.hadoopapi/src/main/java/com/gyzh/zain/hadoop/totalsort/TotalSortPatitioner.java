package com.gyzh.zain.hadoop.totalsort;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * 为什么不用默认分区
 * 默认的是hashcode分区，达不到全排序效果，所以需要自定义分区
 * 此外，需要复习下正则
 * 
 * @author zain
 * 17/04/04
 */
public class TotalSortPatitioner extends Partitioner<IntWritable, IntWritable> {

    @Override
    public int getPartition(IntWritable key, IntWritable value, int numPartitions) {
        if (key.toString().matches("[0-9]") 
                || key.toString().matches("[0-9][0-9]")) {
            return 0;
        }
        if (key.toString().matches("[0-9][0-9][0-9]")) {
            return 1;
        }
        return 2;
    }

}
