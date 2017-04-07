package com.gyzh.zain.hadoop.custom.word;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCountDriver {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        
        job.setJarByClass(WordCountDriver.class);
        job.setMapperClass(WordCountMapper.class);
        job.setMapOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(Text.class);
        
        job.setInputFormatClass(AuthInputFormat.class);
        
        FileInputFormat.setInputPaths(job, new Path("hdfs:///"));
        FileOutputFormat.setOutputPath(job, new Path(""));
        
        job.waitForCompletion(true);
    }
}
