package com.gyzh.zain.hadoop.custom.score;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.util.LineReader;

public class ScoreRecordReader extends RecordReader<Text, Text>{
	
	private FileSplit fs;
	private Text key;
	private Text value;
	private LineReader reader;

	@Override
	public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
		fs=(FileSplit) split;
		Path path=fs.getPath();	
		Configuration conf=new Configuration();
		FileSystem system=path.getFileSystem(conf);
		FSDataInputStream in=system.open(path);
		reader=new LineReader(in);
		
	}

	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		key=new Text();
		value=new Text();
		Text tmp=new Text();
		int result=reader.readLine(tmp);
		if(result==0){
			return false;
		}else{
			key=tmp;
//			reader.readLine(tmp);
//			//tmp helloworld
//			//world
//			value.append(tmp.getBytes(),0, tmp.getLength());
//			value.append(" ".getBytes(),0,1);
//			reader.readLine(tmp);
//			value.append(tmp.getBytes(),0,tmp.getLength());
			
			//注意临时变量的使用
            //此外，对于空格问题，要想到用trim();
			for(int i=0;i<2;i++){
				Text tm=new Text();
				reader.readLine(tm);
				value.append(tm.getBytes(), 0,tm.getLength());
				value.append(" ".getBytes(),0,1);
			}
			return true;
		}
		
	}

	@Override
	public Text getCurrentKey() throws IOException, InterruptedException {
	
		return key;
	}

	@Override
	public Text getCurrentValue() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return value;
	}

	@Override
	public float getProgress() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void close() throws IOException {
		reader.close();
		
	}

}
