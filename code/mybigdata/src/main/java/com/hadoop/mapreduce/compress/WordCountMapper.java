package com.hadoop.mapreduce.compress;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Mapper 计算类
 *
 * 泛型参数解析:
 * KEYIN, VALUEIN, KEYOUT, VALUEOUT
 * LongWritable, Text, Text, IntWritable
 * * 前两个表示入参的 <K, V>类型, 后两个表示出参的<K, V> 类型
 * * 按照需求, 以文本文档的形式输入文件进行计算, 在Mapper中最终输出解析的单词和次数(次数默认为1)
 * * LongWritable: 入参K, 表示文本文件中该行文本的偏移索引
 * * Text: 入参V, 以字符串的形式读取每一行数据
 * * Text: 出参K, 计算完成后, 将单词作为K输出
 * * IntWritable: 出参V, 在 Mapper 阶段, 不做汇总, 每一个单词都会输出, 无论重复, 默认为1
 * 最终, Mapper 出参的<K, V>会作为Reduce入参的<K, V>继续进行汇总计算
 *
 * @author PJ_ZHANG
 * @create 2021-05-27 18:08
 **/
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private Text text = new Text();

    private IntWritable one = new IntWritable(1);

    /**
     * Mapper阶段, map(..)方法调用的基本单位为行
     * 即文本文件的每一行会调用一次map文件
     * 该行中可能存在多个单词, 需要通过空格拆分处理(简单操作)
     *
     * @param key 当前行在文件中的位置偏移索引
     * @param value 当前行的内容
     * @param context 上下文数据
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        if (StringUtils.isEmpty(value.toString())) {
            return;
        }
        String valueStr = value.toString();
        String[] valueArr = valueStr.split(" ");
        for (String str : valueArr) {
            text.set(str);
            // 写到context中, 作为出参
            // 因为每一个单词都会统计, 所以对于每一个单词, 都默认出现了一次
            // 会在后续Mapper中进行汇总
            context.write(text, one);
        }
    }
}
