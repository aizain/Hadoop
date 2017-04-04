package com.gyzh.zain.hadoop.totalsort;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 全排序
 * @author zain
 * 17/04/04
 */
public class TotalSortDriver {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        
        job.setJarByClass(TotalSortDriver.class);
        job.setMapperClass(TotalSortMapper.class);
        job.setReducerClass(TotalSortReducer.class);
        
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(IntWritable.class);
        
        job.setPartitionerClass(TotalSortPatitioner.class);
        job.setNumReduceTasks(3);
        
        FileInputFormat.setInputPaths(job, new Path("/totalsort/totalsort.txt"));
        FileOutputFormat.setOutputPath(job, new Path("/totalsort/result"));
        
        job.waitForCompletion(true);
    }
}
