package com.gyzh.zain.hadoop.ave;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class AveReducer extends Reducer<Text, IntWritable, Text, Text> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, Text>.Context context)
            throws IOException, InterruptedException {
        int sum = 0;
        int i = 0;
        for (IntWritable value : values) {
            sum += value.get();
            i++;
        }
        int result = sum/i;
        context.write(key, new Text(result + ""));
    }
}
