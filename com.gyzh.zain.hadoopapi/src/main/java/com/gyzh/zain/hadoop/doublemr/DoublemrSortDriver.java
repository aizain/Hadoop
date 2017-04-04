package com.gyzh.zain.hadoop.doublemr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 多个MR任务
 * 用于排序的第二个MR
 * 
 * @author zain
 * 17/04/04
 */
public class DoublemrSortDriver {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        
        job.setJarByClass(DoublemrSortDriver.class);
        job.setMapperClass(DoublemrSortMapper.class);
        
        job.setMapOutputKeyClass(DoublemrBean.class);
        job.setMapOutputValueClass(NullWritable.class);
        
        /**
         * 可以直接指到目录，对于标识文件，MR会自动屏蔽
         */
        FileInputFormat.setInputPaths(job, new Path("/doublemr/result"));
        FileOutputFormat.setOutputPath(job, new Path("/doublemr/resultsort"));
        
        job.waitForCompletion(true);
        
    }
}
