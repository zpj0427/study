package com.hadoop.mapreduce.partition;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * 最终进行数据汇总
 * @author PJ_ZHANG
 * @create 2021-05-28 15:37
 **/
public class SelfReduce extends Reducer<Text, SelfDomain, Text, SelfDomain> {

    private SelfDomain domain = new SelfDomain();

    @Override
    protected void reduce(Text key, Iterable<SelfDomain> values, Context context) throws IOException, InterruptedException {
        long uploadBytes = 0L;
        long downloadBytes = 0L;
        long sumBytes = 0L;
        for (SelfDomain currDomain : values) {
            uploadBytes += currDomain.getUploadBytes();
            downloadBytes += currDomain.getDownloadBytes();
            sumBytes += currDomain.getSumBytes();
            domain.setUploadBytes(uploadBytes);
            domain.setDownloadBytes(downloadBytes);
            domain.setSumBytes(sumBytes);
            context.write(key, domain);
        }
    }
}
