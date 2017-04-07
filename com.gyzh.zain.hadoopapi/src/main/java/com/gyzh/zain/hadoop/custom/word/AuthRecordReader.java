package com.gyzh.zain.hadoop.custom.word;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.util.LineReader;

/**
 * 实现自定义的输出读取
 * @author zain
 * 17/04/07
 */
public class AuthRecordReader extends RecordReader<IntWritable, Text> {
    
    private FileSplit fs;
    private IntWritable key;
    private Text value;
    private LineReader reader;
    private int count = 0;
    
    /**
     * 初始化
     */
    @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        fs = (FileSplit) split;
        Path path = fs.getPath();
        Configuration conf = new Configuration();
        FileSystem system = path.getFileSystem(conf);
        // 拿到文件的输入流，即文件数据
        FSDataInputStream in = system.open(path);
        reader = new LineReader(in);
    }
    
    /**
     * 1.这个方法会被多次调用，当此方法的返回值是true的时候，就会被调用一次
     * 2.每当nextKeyValue被调用一次，getCurrentKey和getCurrentValue也会被调用一次
     * 3.getCurrentKey每调用一次，就会把这个方法的返回值交给map的输入key
     * 4.getCurrentValue每调用一次，就会把这个方法的返回值交给map的输入value
     */
    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        key = new IntWritable();
        value = new Text();
        Text tmp = new Text();
        int result = reader.readLine(tmp);
        if (0 == result) {
            // 证明没有行数据可读
            return false;
        }
        count++;
        key.set(count);
        value = tmp;
        
        return true;
    }

    @Override
    public IntWritable getCurrentKey() throws IOException, InterruptedException {
        return key;
    }

    @Override
    public Text getCurrentValue() throws IOException, InterruptedException {
        return value;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void close() throws IOException {
        if (null != reader) {
            reader.close();
        }
    }

}
