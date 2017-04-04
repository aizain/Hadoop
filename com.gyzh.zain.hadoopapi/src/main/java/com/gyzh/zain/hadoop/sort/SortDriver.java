package com.gyzh.zain.hadoop.sort;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


/**
 * 排序
 * @author zain
 * 17/04/04
 */
public class SortDriver {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        
        job.setJarByClass(SortDriver.class);
        job.setMapperClass(SortMapper.class);
        
        job.setMapOutputKeyClass(SortBean.class);
        job.setMapOutputValueClass(NullWritable.class);
        
        FileInputFormat.setInputPaths(job, new Path("/sort/sort.txt"));
        FileOutputFormat.setOutputPath(job, new Path("/sort/result"));
        
        job.waitForCompletion(true);
    }
}
