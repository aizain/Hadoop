package com.gyzh.zain.hadoop.custom.score;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ScoreMapper1 extends Mapper<LongWritable, Text,Text,Text>{
	
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
			String line=value.toString();
			String name=line.split(" ")[0];
			String score=line.split(name)[1].trim();
			context.write(new Text(name), new Text(score));
	}

}
