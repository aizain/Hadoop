package com.gyzh.zain.hadoop.small;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class CombineSmallfileDriver {
	
	public static void main(String[] args) throws Exception {
		Configuration conf=new Configuration();
	
		Job job =Job.getInstance(conf);
		job.setJarByClass(CombineSmallfileDriver.class);
		job.setMapperClass(CombineSmallfileMapper.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setInputFormatClass(CombineSmallfileInputFormat.class);

		FileInputFormat.setInputPaths(job, new Path("hdfs://192.168.234.191:9000/score"));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://192.168.234.191:9000/score/result"));
		
		job.waitForCompletion(true);
	}

}

