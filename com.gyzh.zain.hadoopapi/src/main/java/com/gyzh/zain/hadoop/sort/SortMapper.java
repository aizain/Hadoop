package com.gyzh.zain.hadoop.sort;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SortMapper extends Mapper<LongWritable, Text, SortBean, NullWritable> {
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, SortBean, NullWritable>.Context context)
            throws IOException, InterruptedException {
        
        String[] mv = value.toString().split(" ");
        String name = mv[0];
        int score = Integer.parseInt(mv[1]);
        SortBean sb = new SortBean();
        sb.setName(name);
        sb.setScore(score);
        context.write(sb, NullWritable.get());
    }
    
}
