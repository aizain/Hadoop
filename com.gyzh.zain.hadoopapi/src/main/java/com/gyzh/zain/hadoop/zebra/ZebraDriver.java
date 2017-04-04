package com.gyzh.zain.hadoop.zebra;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * zebra项目
 * 
 * @author zain
 * 17/04/04
 */
public class ZebraDriver {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        
        job.setJarByClass(ZebraDriver.class);
        job.setMapperClass(ZebraMapper.class);
        job.setReducerClass(ZebraReducer.class);
        
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(HTTPAPPHostBean.class);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(HTTPAPPHostBean.class);
        
        FileInputFormat.setInputPaths(job, new Path("/zebra"));
        FileOutputFormat.setOutputPath(job, new Path("/zebra/result"));
        
        job.waitForCompletion(true);
    }
}
