package com.gyzh.zain.hadoop.word;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * MR计算框架有两个组件，
 * 分别是Mapper和Reduce组件
 * 
 * 总结：
 * key 每行行首的偏移量
 * value 每行内容
 * 
 * Mapper第一个形参 key 行首偏移量 LongWritable 固定的
 * Mapper第二个形参value 每行内容 Text 固定的
 * Mapper第三、四个形参和context.write(key, value)输出的类型对应
 * 
 * 1.Mapper组件会读取文件内容，并通过map方法的两个形参key,value交给程序员，
 * 我们最主要的是拿到value，即每行内容
 * 2.每有一行数据，就会触发一次map方法
 * 3.第三个和第四个形参，要和context输出的key和value类型对应
 * 
 * 常用类型：
 * LongWritable
 * IntWritable
 * Text
 * NullWritable
 * 
 * 1.Mapper任务数量：
 * Mapper任务取决切片的数量，Splits，切片里封装了处理的数据长度以及处理数据位置所在信息
 * 2.切片的数量取决于：
 * 文件总大小/BlockSize
 * BlockSize = 128MB
 * INFO mapreduce.JobSubmitter: number of splits:1
 * 
 * @author zain
 * 17/04/04
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
            throws IOException, InterruptedException {
        // Text和String的转换
        String line = value.toString();
        String[] data = line .split(" ");
        for (String word : data) {
            // 输出
            context.write(new Text(word), new IntWritable(1));
        }
    }
}
