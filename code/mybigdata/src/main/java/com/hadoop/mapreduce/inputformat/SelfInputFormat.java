package com.hadoop.mapreduce.inputformat;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;

/**
 * 模拟TextInputformat
 */
public class SelfInputFormat extends FileInputFormat<LongWritable, Text> {

    @Override
    public RecordReader<LongWritable, Text> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        // 直接返回自定义的 selfRecordReader
        SelfRecordReader selfRecordReader = new SelfRecordReader();
        return selfRecordReader;
    }

    @Override
    protected boolean isSplitable(JobContext context, Path filename) {
        // true: 进行分片处理,
        // false: 不进行分片处理
        return true;
    }
}
