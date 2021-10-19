package com.hadoop.mapreduce.compress;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Reduce汇总节点
 * 参数解析:
 * KEYIN,VALUEIN,KEYOUT,VALUEOUT
 * Text, IntWritable, Text, IntWritable
 * * 首先: Mapper的出参对应Reduce的入参, 则前两个参数确定
 * * 按照需求分析, 最终是以<单词, 出现次数>的形式输出,
 * * 所以输出key为Text, 输出value为IntWritable
 *
 * @author PJ_ZHANG
 * @create 2021-05-27 18:18
 **/
public class WordCountReduce extends Reducer<Text, IntWritable, Text, IntWritable> {

    private IntWritable intWritable = new IntWritable();

    /**
     * Reduce调用该方法时, 是以每一组调用一次
     * Mapper中对每一个单词进行记录, 如: Hello出现了三次, 则在Mapper会写三个<Hello, 1>
     * 在Reduce的前面步骤处理中, 会先对重复key进行汇总, 处理为<K, List<V>>的形式
     * 在调用一次reduce(..)方法时, 是对每一组汇总后的key的统一处理
     *
     * @param key Mapper输出的每一组key
     * @param values 该key对应的数据集合
     * @param context 上下文
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        // 通过sum进行总数记录
        // 对记录的数据进行叠加
        for (IntWritable intWritable : values) {
            sum += intWritable.get();
        }
        intWritable.set(sum);
        // 最终写出单词出现的次数
        context.write(key, intWritable);
    }
}
