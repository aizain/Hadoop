package com.gyzh.zain.hadoop.flow;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * 自定义分区
 * 
 * 第一个泛型，Mapper输出的key的类型
 * 第二个泛型，Mapper输出的value的类型
 * 
 * 先分区再按key合并
 * 
 * @author zain
 * 17/04/04
 */
public class FlowPartitioner extends Partitioner<Text, FlowBean> {

    @Override
    public int getPartition(Text key, FlowBean value, int numPartitions) {
        if (value.getAddr().equals("sh")) {
            return 0;
        } 
        if (value.getAddr().equals("bj")) {
            return 1;
        }
        return 2;
    }

}
