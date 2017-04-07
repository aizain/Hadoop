package com.gyzh.zain.hadoop.custom.outputfomat;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

public class ScoreReducer extends Reducer<Text, Text, Text, Text>{
    //注意泛型和reducer的第三个和第四个泛型对应上
    //注意包不要导错
	private MultipleOutputs<Text,Text> mos;
	
	@Override
	protected void setup(Reducer<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {
		mos=new MultipleOutputs<>(context);
	}
	

	@Override
	protected void reduce(Text name, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		
		for(Text value:values){
			if(name.toString().equals("tom")){
				mos.write("tominfo",name, value);
			}
			else if(name.toString().equals("rose")){
				mos.write("roseinfo", name, value);
			}else {
				mos.write("otherinfo", name, value);
			}
		}
		
	}
}
