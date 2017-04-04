package com.gyzh.zain.hadoop.totalsort;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class TotalSortReducer extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {
    @Override
    protected void reduce(IntWritable key, Iterable<IntWritable> values,
            Reducer<IntWritable, IntWritable, IntWritable, IntWritable>.Context context)
            throws IOException, InterruptedException {
        
        int result = 0;
        for (IntWritable value : values) {
            result += value.get();
        }
        context.write(key, new IntWritable(result));
    }
}
