package com.gyzh.zain.hadoop.score;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 统计成绩
 * 每个人，每一科三个月的总成绩
 * 
 * @author zain
 * 17/04/04
 */
public class ScoreDriver {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        
        job.setJarByClass(ScoreDriver.class);
        job.setMapperClass(ScoreMapper.class);
        job.setReducerClass(ScoreReducer.class);
        
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(ScoreBean.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(ScoreBean.class);
        
        FileInputFormat.setInputPaths(job, new Path("/score"));
        FileOutputFormat.setOutputPath(job, new Path("/score/result"));
        
        job.waitForCompletion(true);
    }
}
