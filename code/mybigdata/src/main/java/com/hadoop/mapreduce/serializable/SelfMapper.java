package com.hadoop.mapreduce.serializable;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 自定义Mapper计算
 * LongWritable, Text: 以行的形式读取数据
 * Text, SelfDomain: 自定义value输出数据
 * 基本数据格式:
 * ID	手机号	IP地址	IP域名	上行流量	下行流量	网络状态
 * 1	18291166067	192.168.10.0	www.baidu.com	1123	112	200
 * 需求分析:
 * 输入: 以上文本数据, 整体为一个文本列表
 * 输出: 每一个手机号对应的上行流量,下行流量,总流量汇总
 * @author PJ_ZHANG
 * @create 2021-05-28 14:44
 **/
public class SelfMapper extends Mapper<LongWritable, Text, Text, SelfDomain> {

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
        String phone = strArr[1];
        long uploadBytes = Long.parseLong(strArr[4]);
        long downloadBytes = Long.parseLong(strArr[5]);
        long sumBytes = uploadBytes + downloadBytes;
        domain.setUploadBytes(uploadBytes);
        domain.setDownloadBytes(downloadBytes);
        domain.setSumBytes(sumBytes);
        text.set(phone);
        context.write(text, domain);
    }
}
