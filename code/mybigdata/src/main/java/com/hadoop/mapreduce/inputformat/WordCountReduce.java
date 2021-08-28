package com.hadoop.mapreduce.inputformat;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class WordCountReduce extends Reducer<LongWritable, Text, LongWritable, Text> {

    private IntWritable intWritable = new IntWritable();

    @Override
    protected void reduce(LongWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        for (Text text : values) {
            context.write(key, text);
        }
    }
}
