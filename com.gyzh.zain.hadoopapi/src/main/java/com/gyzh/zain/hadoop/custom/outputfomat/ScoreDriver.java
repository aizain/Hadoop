package com.gyzh.zain.hadoop.custom.outputfomat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;



public class ScoreDriver {
	public static void main(String[] args) throws Exception {
		Configuration conf=new Configuration();
		Job job=Job.getInstance(conf);
		
		job.setJarByClass(ScoreDriver.class);
		job.setMapperClass(ScoreMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setReducerClass(ScoreReducer.class);
		//job.setOutputKeyClass(Text.class);
		//job.setOutputValueClass(Text.class);
		
		job.setOutputFormatClass(AuthOutputformat.class);
		
		//注意，Hadoop多输出源，虽然生成多个结果文件，但是实现的思想不是分区，这个要区别开，因为是reduce阶段发生的
		MultipleOutputs.addNamedOutput(job,"tominfo",TextOutputFormat.class, 
				Text.class, Text.class);
		MultipleOutputs.addNamedOutput(job,"roseinfo",AuthOutputformat.class, 
				Text.class, Text.class);
		MultipleOutputs.addNamedOutput(job,"otherinfo",AuthOutputformat.class, 
				Text.class, Text.class);
		
		
		
		
		FileInputFormat.setInputPaths(job,new Path("hdfs://192.168.234.191:9000/score"));
		FileOutputFormat.setOutputPath(job,new Path("hdfs://192.168.234.191:9000/score/result"));
		
		job.waitForCompletion(true);
		
	}
}
