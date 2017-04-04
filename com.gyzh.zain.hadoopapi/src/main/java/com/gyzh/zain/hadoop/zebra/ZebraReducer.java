package com.gyzh.zain.hadoop.zebra;

import java.io.IOException;
import java.sql.Timestamp;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ZebraReducer extends Reducer<Text, HTTPAPPHostBean, NullWritable, HTTPAPPHostBean> {
    @Override
    protected void reduce(Text key, Iterable<HTTPAPPHostBean> values,
            Reducer<Text, HTTPAPPHostBean, NullWritable, HTTPAPPHostBean>.Context context)
            throws IOException, InterruptedException {
        
        HTTPAPPHostBean hah = new HTTPAPPHostBean();
        int i=0;
        for (HTTPAPPHostBean value : values) {
            if (i == 0) {
                HTTPAPPHostBean rpchah = value;
                hah.setCellid(rpchah.getCellid());
                hah.setAppType(rpchah.getAppType());
                hah.setAppSubtype(rpchah.getAppSubtype());
                hah.setUserIP(rpchah.getUserIP());
                hah.setUserPort(rpchah.getUserPort());
                hah.setAppServerIP(rpchah.getAppServerIP());
                hah.setAppServerPort(rpchah.getAppServerPort());
                hah.setHost(rpchah.getHost());
                hah.setReportTime(rpchah.getReportTime());
                hah.setSliceType(rpchah.getSliceType());
                hah.setSlice(rpchah.getSlice());
                hah.setShufflekey(rpchah.getShufflekey());
            }
            i++;
            
            value.setAttempts(value.getAttempts() + hah.getAttempts());
            value.setAccepts(value.getAccepts() + hah.getAccepts());
            value.setTrafficUL(value.getTrafficUL() + hah.getTrafficUL());
            value.setTrafficDL(value.getAttempts() + hah.getAttempts());
            value.setRetranUL(value.getRetranUL() + hah.getRetranUL());
            value.setRetranDL(value.getRetranDL() + hah.getRetranDL());
            value.setFailCount(value.getFailCount() + hah.getFailCount());
            value.setTransDelay(value.getTransDelay() + hah.getTransDelay());
        }
        
        context.write(NullWritable.get(), hah);
    }
}
