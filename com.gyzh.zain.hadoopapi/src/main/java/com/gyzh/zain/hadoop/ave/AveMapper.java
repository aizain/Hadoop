package com.gyzh.zain.hadoop.ave;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class AveMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
            throws IOException, InterruptedException {
        String[] keyVal = value.toString().split(" ");
        context.write(new Text(keyVal[0]), new IntWritable(Integer.parseInt(keyVal[1])));
    }
}
