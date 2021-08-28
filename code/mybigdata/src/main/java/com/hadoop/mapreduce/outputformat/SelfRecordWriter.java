package com.hadoop.mapreduce.outputformat;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SelfRecordWriter extends RecordWriter<Text, NullWritable> {

    private FSDataOutputStream helloOutputFormat;

    private FSDataOutputStream otherOutputFormat;

    public SelfRecordWriter(TaskAttemptContext job) {
        try {
            FileSystem fs = FileSystem.get(job.getConfiguration());
            helloOutputFormat = fs.create(new Path("E:\\hello.txt"));
            otherOutputFormat = fs.create(new Path("E:\\other.txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(Text key, NullWritable value) throws IOException, InterruptedException {
        String line = key.toString();
        if (line.contains("hello")) {
            helloOutputFormat.write((line + "\n").getBytes(StandardCharsets.UTF_8));
        } else {
            otherOutputFormat.write((line + "\n").getBytes(StandardCharsets.UTF_8));
        }
    }

    @Override
    public void close(TaskAttemptContext context) throws IOException, InterruptedException {
        IOUtils.closeStreams(helloOutputFormat, otherOutputFormat);
    }

}
