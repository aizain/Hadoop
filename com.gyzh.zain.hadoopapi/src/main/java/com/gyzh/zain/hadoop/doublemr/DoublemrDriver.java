package com.gyzh.zain.hadoop.doublemr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 多个MR任务
 * 计算利润并排序
 * 
 * @author zain
 * 17/04/04
 */
public class DoublemrDriver {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        
        job.setJarByClass(DoublemrDriver.class);
        job.setMapperClass(DoublemrMapper.class);
        job.setReducerClass(DoublemrReducer.class);
        
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(DoublemrBean.class);
        job.setOutputKeyClass(DoublemrBean.class);
        job.setOutputValueClass(NullWritable.class);
        
        FileInputFormat.setInputPaths(job, new Path("/doublemr/doublemr.txt"));
        FileOutputFormat.setOutputPath(job, new Path("/doublemr/result"));
        
        job.waitForCompletion(true);
        
    }
}
