package com.gyzh.zain.hadoop.custom.score;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ScoreDriver {

	public static void main(String[] args) throws Exception {
		Configuration conf=new Configuration();
		Job job=Job.getInstance(conf);
		
		job.setJarByClass(ScoreDriver.class);
		//job.setMapperClass(ScoreMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		
		//job.setInputFormatClass(ScoreInputFormat.class);
		
		//Hadoop 多输入源使用事项说明：
        //1.不能指定job.setMapperClass(ScoreMapper.class)这个方法，因为有多个mapper
        //2.setInputFormatClass(ScoreInputFormat.class)这个方法也没有意义，可以不用调用
        //3.FileInputFormat.setInputPaths 也不用调用了
        //4.多个Mapper的输出key和value的泛型要一致
		MultipleInputs.addInputPath(job, new Path("hdfs://192.168.234.191:9000/score/score.txt"),
				ScoreInputFormat.class,ScoreMapper.class);
		
		MultipleInputs.addInputPath(job, new Path("hdfs://192.168.234.191:9000/score/score1.txt"),
				TextInputFormat.class,ScoreMapper1.class);
		
		
		
		//FileInputFormat.setInputPaths(job,new Path("hdfs://192.168.234.191:9000/score"));
		FileOutputFormat.setOutputPath(job,new Path("hdfs://192.168.234.191:9000/score/result"));
		
		job.waitForCompletion(true);
		
	}
}
