package com.gyzh.zain.hadoop.score;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class ScoreMapper extends Mapper<LongWritable, Text, Text, ScoreBean> {
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, ScoreBean>.Context context)
            throws IOException, InterruptedException {
        FileSplit split = (FileSplit) context.getInputSplit();
        // 获取文件名
        String fileName = split.getPath().getName();
        
        String[] values = value.toString().split(" ");
        String month = values[0];
        String name = values[1];
        String score = values[2];
        
        ScoreBean sb = new ScoreBean();
        sb.setName(name);
        if (fileName.startsWith("chinese")) {
            sb.setChinese(Integer.parseInt(score));
        } else if (fileName.startsWith("english")) {
            sb.setEnglish(Integer.parseInt(score));
        } else if (fileName.startsWith("math")) {
            sb.setMath(Integer.parseInt(score));
        } else {
        }
        context.write(new Text(sb.getName()), sb);
    }
}
