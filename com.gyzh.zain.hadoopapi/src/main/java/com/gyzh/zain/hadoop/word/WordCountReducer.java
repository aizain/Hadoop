package com.gyzh.zain.hadoop.word;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * Reducer组件会接收到Mapper组件的输出结果
 * 第一个形参类型：Mapper输出的key类型
 * 第二个形参类型：Mapper输出的value类型
 * 第三、四个形参类型：用户自定义的输出结果
 * 
 * 注意：
 * 1.对于MR，Mapper组件可以单独工作
 * 但是Reducer组件不能单独工作，必须得依赖Mapper组件，因为需要Mapper组件的输出结果
 * 2.引入Reducer组件之后，输出的结果文件就是reducer组件的输出结果
 * 
 * Reducer工作机制：
 * 1.根据key，把key对应的value值合在一起，形成Iterable交给程序员
 * 所以，reduce方法执行的次数取决于Mapper输出的key（不同的key）
 * 2.Reducer会对key进行排序
 * 
 * Reducer任务数量：
 * Hadoop默认就是一个
 * 此外，结果文件的数量=Reducer任务数量
 * 
 * 分区的概念：
 * Hadoop默认分区用的是HashPartitioner，
 * 根据Mapper输出的key的hashcode进行分区
 * 
 * @author zain
 * 17/04/04
 */
public class WordCountReducer extends Reducer<Text, IntWritable, Text, Text> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values,
            Reducer<Text, IntWritable, Text, Text>.Context context) throws IOException, InterruptedException {
        int result = 0;
        for (IntWritable value : values) {
            // IntWritable -> int 调用get()方法
            result += value.get();
        }
        context.write(key, new Text(result + ""));
    }
}
