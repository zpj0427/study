package com.hadoop.mapreduce.combiner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * Driver类中进行统一调度
 * 分8个步骤
 * @author PJ_ZHANG
 * @create 2021-05-27 18:24
 **/
public class WordCountDriver {

    public static void main(String[] args) throws Exception {
        // 1. 获取配置信息, 获取Job示例
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);
        // 2. 指定本程序jar包所在的路径
        job.setJarByClass(WordCountDriver.class);
        // 3. 关联Mapper/Reduce业务类
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReduce.class);
        // 4. 指定Mapper输出数据的KV类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        // 5. 指定Reduce输出数据的KV类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // 指定合并类
        job.setCombinerClass(SelfCombiner.class);

        // 6. 指定Job输入原始数据的文件路径
         FileInputFormat.setInputPaths(job, new Path("E:\\123.txt"));
        // 7. 指定Job输出结果数据的文件路径
         FileOutputFormat.setOutputPath(job, new Path("E:\\wcout"));
        // 8. 提交执行
        job.waitForCompletion(true);
    }

}
