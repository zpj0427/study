package com.hadoop.mapreduce.sort;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 自定义Mapper计算
 * 按总流量进行排序, 需要把总流浪所在的对象设置为key, 手机号为value
 * @author PJ_ZHANG
 * @create 2021-05-28 14:44
 **/
public class SelfMapper extends Mapper<LongWritable, Text, SelfDomain, Text> {

    private SelfDomain domain = new SelfDomain();

    private Text text = new Text();

    /**
     * 以行的形式进行数据读取
     * 1	18291166067	192.168.10.0	www.baidu.com	1123	112	200
     * @param key 偏移量
     * @param value 当前行数据
     * @param context 上下文数据
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String str = value.toString();
        String[] strArr = str.split("\t");
        String phone = strArr[0];
        long uploadBytes = Long.parseLong(strArr[1]);
        long downloadBytes = Long.parseLong(strArr[2]);
        long sumBytes = Long.parseLong(strArr[3]);
        domain.setUploadBytes(uploadBytes);
        domain.setDownloadBytes(downloadBytes);
        domain.setSumBytes(sumBytes);
        text.set(phone);
        context.write(domain, text);
    }
}
