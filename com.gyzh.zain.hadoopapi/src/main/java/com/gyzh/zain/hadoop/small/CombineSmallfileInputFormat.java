package com.gyzh.zain.hadoop.small;
import java.io.IOException;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.CombineFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.CombineFileRecordReader;
import org.apache.hadoop.mapreduce.lib.input.CombineFileSplit;

public class CombineSmallfileInputFormat extends CombineFileInputFormat<LongWritable, BytesWritable>{

	@Override
	public RecordReader<LongWritable, BytesWritable> createRecordReader(InputSplit split, TaskAttemptContext context)
			throws IOException {
		CombineFileSplit combineFileSplit = (CombineFileSplit) split;
		//这里比较重要的是，一定要通过CombineFileRecordReader来创建一个RecordReader，
        //而且它的构造方法的参数必须是上面的定义的类型和顺序，构造方法包含3个参数：
        //第一个是CombineFileSplit类型，第
        //二个是TaskAttemptContext类型，第三个是Class<? extends RecordReader>类型。
		
		CombineFileRecordReader<LongWritable, BytesWritable> recordReader =
		new CombineFileRecordReader<LongWritable, BytesWritable>(combineFileSplit, context, CombineSmallfileRecordReader.class);
		try {
			recordReader.initialize(combineFileSplit, context);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return recordReader;
	}

}
