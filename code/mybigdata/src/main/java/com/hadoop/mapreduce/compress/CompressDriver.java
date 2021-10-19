package com.hadoop.mapreduce.compress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.BZip2Codec;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.DefaultCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class CompressDriver {

    public static void main(String[] args) throws Exception {
        // 1. 获取配置信息, 获取Job示例
        Configuration configuration = new Configuration();
//        // 启用 mapper 端输出压缩
//        configuration.setBoolean("mapreduce.map.output.compress", true);
//        // 设置压缩方式
//        // 最后一个参数表示接口
//        configuration.setClass("mapreduce.map.output.compress.codec", BZip2Codec.class, CompressionCodec.class);
        Job job = Job.getInstance(configuration);
        // 2. 指定本程序jar包所在的路径
        job.setJarByClass(CompressDriver.class);
        // 3. 关联Mapper/Reduce业务类
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReduce.class);
        // 4. 指定Mapper输出数据的KV类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        // 5. 指定Reduce输出数据的KV类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        // 6. 指定Job输入原始数据的文件路径
         FileInputFormat.setInputPaths(job, new Path("E:\\hadoop\\123.txt"));
        // 7. 指定Job输出结果数据的文件路径
        FileOutputFormat.setOutputPath(job, new Path("E:\\hadoop\\selfout" + System.currentTimeMillis()));

        // Reduce输出压缩
        // 打开解压开发
        FileOutputFormat.setCompressOutput(job, true);
        // 解压方式
        FileOutputFormat.setOutputCompressorClass(job, BZip2Codec.class);
//        FileOutputFormat.setOutputCompressorClass(job, GzipCodec.class);
//        FileOutputFormat.setOutputCompressorClass(job, DefaultCodec.class);

        // 8. 提交执行
        job.waitForCompletion(true);
    }

}
