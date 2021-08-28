package com.hadoop.mapreduce.sort;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * 最终进行数据汇总
 * @author PJ_ZHANG
 * @create 2021-05-28 15:37
 **/
public class SelfReduce extends Reducer<SelfDomain, Text, Text, SelfDomain> {

    private SelfDomain domain = new SelfDomain();

    /**
     * reduce对应进行入参的key-value调整, 写出还是以value-key形式写出, 保证写出顺序
     * @param key
     * @param values
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(SelfDomain key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        for (Text phone : values) {
           context.write(phone, key);
        }
    }

}
