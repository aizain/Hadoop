package com.gyzh.zain.hadoop.flow;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class FlowMapper extends Mapper<LongWritable, Text, Text, FlowBean> {
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, FlowBean>.Context context)
            throws IOException, InterruptedException {
        String[] values = value.toString().split(" ");
        FlowBean flowBean = new FlowBean();
        flowBean.setPhone(values[0]);
        flowBean.setAddr(values[1]);
        flowBean.setName(values[2]);
        flowBean.setFlow(Integer.parseInt(values[3]));
        context.write(new Text(flowBean.getName()), flowBean);
    }
}
