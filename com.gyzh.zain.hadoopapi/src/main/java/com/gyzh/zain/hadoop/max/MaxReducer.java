package com.gyzh.zain.hadoop.max;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MaxReducer extends Reducer<Text, IntWritable, Text, Text> {
       @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, Text>.Context context)
            throws IOException, InterruptedException {
           int result = 0;
           for (IntWritable value : values) {
               result = Math.max(result, value.get());
           }
           context.write(key, new Text(result + ""));
    }

}
