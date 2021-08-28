package com.hadoop.mapreduce.partition;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * 自定义分区, key value 为mapper阶段输出的key value类型
 */
public class MyPartition extends Partitioner<Text, SelfDomain> {

    @Override
    public int getPartition(Text text, SelfDomain selfDomain, int numPartitions) {
        String phone = text.toString();
        String prePhone = phone.substring(0, 3);
        if ("136".equals(prePhone)) {
            return 0;
        } else if ("137".equals(prePhone)) {
            return 1;
        } else if ("138".equals(prePhone)) {
            return 2;
        } else if ("139".equals(prePhone)) {
            return 3;
        } else {
            return 4;
        }
    }

}
