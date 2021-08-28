package com.hadoop.mapreduce.reducejoin;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * 自定义Mapper
 * 入参: 行号, 行数据
 * 出参: 商品ID, 自定义列表
 */
public class JoinMapper extends Mapper<LongWritable, Text, Text, MyBean> {

    private String tableName = null;

    private Text outKey = new Text();

    /**
     * 数据初始化, 取表名称, 对应的表关键字
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);
        FileSplit fileSplit = (FileSplit) context.getInputSplit();
        tableName = fileSplit.getPath().getName();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 解析数据
        String productId = null;
        String orderId = null;
        String productName = null;
        Integer productCount = null;
        String[] strArr = value.toString().split("\t");
        if (tableName.contains("order")) {
            // 订单表
            orderId = strArr[0];
            productId = strArr[1];
            productCount = Integer.valueOf(strArr[2]);
        } else {
            // 商品表
            productId = strArr[0];
            productName = strArr[1];
        }
        // 组合并写出数据
        MyBean myBean = new MyBean();
        myBean.setOrderId(null == orderId ? "" : orderId);
        myBean.setProductName(null == productName ? "" : productName);
        myBean.setProductCount(null == productCount ? 0 : productCount);
        myBean.setTableName(tableName);
        outKey.set(productId);
        context.write(outKey, myBean);
    }

}
