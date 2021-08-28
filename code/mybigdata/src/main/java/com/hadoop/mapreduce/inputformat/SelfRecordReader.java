package com.hadoop.mapreduce.inputformat;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.util.LineReader;

import java.io.IOException;

public class SelfRecordReader extends RecordReader<LongWritable, Text> {

    /**
     * 行数据读取
     */
    private LineReader lineReader;

    private LongWritable key = new LongWritable(-1);

    private Text value = new Text();

    private Long currPos;

    private Long start;

    private Long end;

    private Text currLine = new Text();

    @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        FileSplit fileSplit = (FileSplit) split;
        // 取文件路径, 构造文件输入流
        Path filePath = fileSplit.getPath();
        FileSystem fs = filePath.getFileSystem(context.getConfiguration());
        FSDataInputStream is = fs.open(filePath);
        lineReader = new LineReader(is, context.getConfiguration());
        // 获取分片文件的开始位置
        start = fileSplit.getStart();
        // 获取分片文件的结束位置
        end = start + fileSplit.getLength();
        // 读取位置定位到start的位置
        is.seek(start);
        if (start != 0) {
            // 此处大致意思是跳过一个断行, 非第一个分片可能是从行中间某一个位置开始的, 跳过改行, 改行已在上一个分片处理
            start += lineReader.readLine(new Text(), 0, (int) Math.min(Integer.MAX_VALUE, end - start));
        }
        // 定义当前偏移量到开始位置
        currPos = start;
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        if (currPos > end) {
            return false;
        }
        // 读取一行, 并移动偏移量
        currPos += lineReader.readLine(currLine);
        if (0 == currLine.getLength()) {
            return false;
        }
        // 行内容
        value.set(currLine);
        // 行数, 从0行开始
        key.set(key.get() + 1);
        return true;
    }

    @Override
    public LongWritable getCurrentKey() throws IOException, InterruptedException {
        // 取当前key
        return key;
    }

    @Override
    public Text getCurrentValue() throws IOException, InterruptedException {
        // 取当前value
        return value;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        // 取当前进度, 已经执行的百分比
        if (start == end) {
            return 0.0f;
        } else {
            return Math.min(1.0f, (currPos - start) / (float) (end - start));
        }
    }

    @Override
    public void close() throws IOException {
        IOUtils.closeStreams(lineReader);
    }
}
