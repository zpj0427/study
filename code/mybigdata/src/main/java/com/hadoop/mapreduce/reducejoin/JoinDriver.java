package com.hadoop.mapreduce.reducejoin;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class JoinDriver {

    public static void main(String[] args) throws Exception {
        // 获取配置信息, 构建Job示例
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);
        // 指定本程序的jar包路径
        job.setJarByClass(JoinDriver.class);
        // 关联 Mapper/Reduce 业务类
        job.setMapperClass(JoinMapper.class);
        job.setReducerClass(JoinReduce.class);
        // 指定Mapper输出的KV类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(MyBean.class);
        // 指定Reduce输出的KV类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(MyBean.class);
        // 指定job输入路径
        FileInputFormat.setInputPaths(job, new Path("E:\\hadoop\\join"));
        // 指定job输出路径
        FileOutputFormat.setOutputPath(job, new Path("E:\\hadoop\\selfout" + System.currentTimeMillis()));
        // 工作
        job.waitForCompletion(true);
    }

}
