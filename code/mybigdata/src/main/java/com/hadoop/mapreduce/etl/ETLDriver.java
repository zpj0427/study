package com.hadoop.mapreduce.etl;

import com.hadoop.mapreduce.inputformat.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class ETLDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        // 1. 获取配置信息, 获取Job示例
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);
        // 2. 指定本程序jar包所在的路径
        job.setJarByClass(ETLDriver.class);
        // 3. 关联Mapper/Reduce业务类
        job.setMapperClass(ETLMapper.class);
        // 4. 指定Mapper输出数据的KV类型
        job.setMapOutputKeyClass(LongWritable.class);
        job.setMapOutputValueClass(Text.class);
        // 5. 不需要Reduce 设置数量
        job.setNumReduceTasks(0);
        // 6. 指定Job输入原始数据的文件路径
        FileInputFormat.setInputPaths(job, new Path("E:\\hadoop\\etl.txt"));
        // 7. 指定Job输出结果数据的文件路径
        // 这一步需要保留，用于输出_SUCCESS信息
        FileOutputFormat.setOutputPath(job, new Path("E:\\hadoop\\selfout" + System.currentTimeMillis()));
        // 8. 提交执行
        job.waitForCompletion(true);
    }

}
