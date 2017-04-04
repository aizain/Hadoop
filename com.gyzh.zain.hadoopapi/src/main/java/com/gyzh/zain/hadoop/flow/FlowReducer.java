package com.gyzh.zain.hadoop.flow;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class FlowReducer extends Reducer<Text, FlowBean, Text, FlowBean> {
    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Reducer<Text, FlowBean, Text, FlowBean>.Context context)
            throws IOException, InterruptedException {
        FlowBean flowBean = new FlowBean();
        for (FlowBean fb : values) {
            flowBean.setAddr(fb.getAddr());
            flowBean.setPhone(fb.getPhone());
            flowBean.setName(fb.getName());
            flowBean.setFlow(flowBean.getFlow() + fb.getFlow());
        }
        context.write(key, flowBean);
    }

}
