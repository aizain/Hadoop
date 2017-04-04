package com.gyzh.zain.hadoop.doublemr;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class DoublemrSortMapper extends Mapper<LongWritable, Text, DoublemrBean, NullWritable> {
    @Override
    protected void map(LongWritable key, Text value,
            Mapper<LongWritable, Text, DoublemrBean, NullWritable>.Context context)
            throws IOException, InterruptedException {
        String[] datas = value.toString().split(" ");
        DoublemrBean db = new DoublemrBean();
        db.setName(datas[0]);
        db.setProfit(Integer.parseInt(datas[1]));
        
        context.write(db, NullWritable.get());
    }
}
