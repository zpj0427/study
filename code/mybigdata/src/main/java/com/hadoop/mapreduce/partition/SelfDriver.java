package com.hadoop.mapreduce.partition;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 调度类
 *
 * @author PJ_ZHANG
 * @create 2021-05-28 15:41
 **/
public class SelfDriver {

    public static void main(String[] args) throws Exception {
        // 获取配置信息, 构建Job示例
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);
        // 指定本程序的jar包路径
        job.setJarByClass(SelfDriver.class);
        // 关联 Mapper/Reduce 业务类
        job.setMapperClass(SelfMapper.class);
        job.setReducerClass(SelfReduce.class);
        // 指定Mapper输出的KV类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(SelfDomain.class);
        // 设置最大处理分片大小
        // 指定Reduce输出的KV类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(SelfDomain.class);

        // 分区处理, 设置分区处理类
        job.setPartitionerClass(MyPartition.class);
        // 设置ReduceTask个数, 与分区逻辑个数保持一致
        job.setNumReduceTasks(5);


        // 指定job输入路径
        FileInputFormat.setInputPaths(job, new Path("E:\\hadoop\\123456.txt"));
        // 指定job输出路径
        FileOutputFormat.setOutputPath(job, new Path("E:\\hadoop\\selfout" + System.currentTimeMillis()));
        // 工作
        job.waitForCompletion(true);
    }

}
