package com.hadoop.mapreduce.combiner;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class WordCountReduce extends Reducer<Text, IntWritable, Text, IntWritable> {

    private IntWritable intWritable = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        // 通过sum进行总数记录
        // 对记录的数据进行叠加
        for (IntWritable intWritable : values) {
            sum += intWritable.get();
        }
        intWritable.set(sum);
        // 最终写出单词出现的次数
        context.write(key, intWritable);
    }
}
