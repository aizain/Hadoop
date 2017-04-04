package com.gyzh.zain.hadoop.max;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MaxMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
     @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
            throws IOException, InterruptedException {
         String year = "";
         int tem = 0;
         
         year = value.toString().substring(9, 13);
         tem = Integer.parseInt(value.toString().substring(21, 23));
         
         context.write(new Text(year), new IntWritable(tem));
    }

}
