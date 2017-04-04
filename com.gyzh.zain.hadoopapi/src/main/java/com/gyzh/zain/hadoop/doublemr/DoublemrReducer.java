package com.gyzh.zain.hadoop.doublemr;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class DoublemrReducer extends Reducer<Text, DoublemrBean, DoublemrBean, NullWritable> {
    @Override
    protected void reduce(Text key, Iterable<DoublemrBean> values,
            Reducer<Text, DoublemrBean, DoublemrBean, NullWritable>.Context context)
            throws IOException, InterruptedException {
        DoublemrBean db = new DoublemrBean();
        db.setName(key.toString());
        for (DoublemrBean value : values) {
            db.setProfit(db.getProfit() + value.getProfit());
        }
        context.write(db, NullWritable.get());
    }
}
