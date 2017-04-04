package com.gyzh.zain.hadoop.doublemr;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class DoublemrMapper extends Mapper<LongWritable, Text, Text, DoublemrBean> {
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, DoublemrBean>.Context context)
            throws IOException, InterruptedException {
        String[] datas = value.toString().split(" ");
        DoublemrBean db = new DoublemrBean();
        db.setName(datas[1]);
        db.setProfit(Integer.parseInt(datas[2]) - Integer.parseInt(datas[3]));
        
        context.write(new Text(db.getName()), db);
    }
}
