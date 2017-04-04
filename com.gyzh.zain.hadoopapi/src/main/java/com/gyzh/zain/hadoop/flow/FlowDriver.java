package com.gyzh.zain.hadoop.flow;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 统计流量
 * @author zain
 * 17/04/04
 */
public class FlowDriver {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        
        job.setJarByClass(FlowDriver.class);
        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);
        
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);
        
        job.setNumReduceTasks(3);
        job.setPartitionerClass(FlowPartitioner.class);
        
        FileInputFormat.setInputPaths(job, new Path("/flow/flow.txt"));
        FileOutputFormat.setOutputPath(job, new Path("/flow/result"));
        
        job.waitForCompletion(true);
    }
}
