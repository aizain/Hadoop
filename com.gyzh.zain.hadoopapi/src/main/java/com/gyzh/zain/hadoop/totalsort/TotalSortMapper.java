package com.gyzh.zain.hadoop.totalsort;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TotalSortMapper extends Mapper<LongWritable, Text, IntWritable, IntWritable> {
    @Override
    protected void map(LongWritable key, Text value,
            Mapper<LongWritable, Text, IntWritable, IntWritable>.Context context)
            throws IOException, InterruptedException {
        String[] datas = value.toString().split(" ");
        for (String num : datas) {
            context.write(new IntWritable(Integer.parseInt(num)), new IntWritable(1));
        }
    }
}
