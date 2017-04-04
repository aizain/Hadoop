package com.gyzh.zain.hadoop.score;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ScoreReducer extends Reducer<Text, ScoreBean, Text, ScoreBean> {
    @Override
    protected void reduce(Text key, Iterable<ScoreBean> values, Reducer<Text, ScoreBean, Text, ScoreBean>.Context context)
            throws IOException, InterruptedException {
        
        ScoreBean sb = new ScoreBean();
        sb.setName(key.toString());
        for (ScoreBean value : values) {
            sb.setChinese(value.getChinese() + sb.getChinese());
            sb.setEnglish(value.getEnglish() + sb.getEnglish());
            sb.setMath(value.getMath() + sb.getMath());
        }
        
        context.write(key, sb);
    }
}
