package com.hadoop.mapreduce.outputformat;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class WordCountReduce extends Reducer<Text, NullWritable, Text, NullWritable> {

    private IntWritable intWritable = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        for (NullWritable nullWritable : values) {
            context.write(key, NullWritable.get());
        }
    }
}
