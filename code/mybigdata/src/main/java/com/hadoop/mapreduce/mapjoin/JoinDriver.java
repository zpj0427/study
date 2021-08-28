package com.hadoop.mapreduce.mapjoin;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.net.URI;

public class JoinDriver {

    public static void main(String[] args) throws Exception {
        // 获取配置信息, 构建Job示例
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);
        // 指定本程序的jar包路径
        job.setJarByClass(JoinDriver.class);
        // 关联 Mapper/Reduce 业务类
        job.setMapperClass(JoinMapper.class);
        // 指定Mapper输出的KV类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);
        //设置map缓存路径
        job.addCacheFile(new URI("file:///E:/hadoop/tmp.txt"));
        // 只走map阶段, 不走reduce阶段
        job.setNumReduceTasks(0);
        // 指定job输入路径
        FileInputFormat.setInputPaths(job, new Path("E:\\hadoop\\mapjoin.txt"));
        // 指定job输出路径
        FileOutputFormat.setOutputPath(job, new Path("E:\\hadoop\\selfout" + System.currentTimeMillis()));
        // 工作
        job.waitForCompletion(true);
    }

}
