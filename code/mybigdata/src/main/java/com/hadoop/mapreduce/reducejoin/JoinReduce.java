package com.hadoop.mapreduce.reducejoin;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义Reduce类
 * 入参: 与Mapper出参对应, 商品ID, 自定义Bean
 * 出参: 与上面一致
 */
public class JoinReduce extends Reducer<Text, MyBean, Text, MyBean> {

    /**
     * 注意, 在Mapper中对两张表的数据, 以同一个key输出,
     * 所以, 在reduce中, value是两张表的数据在一起, 以tableName字段识别, 先需要对数据进行分离
     * @param key
     * @param values
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(Text key, Iterable<MyBean> values, Context context) throws IOException, InterruptedException {
        // 数据分离
        List<MyBean> lstOrderData = new ArrayList<>();
        Map<String, String> productId2Name = new HashMap<>();
        for (MyBean value : values) {
            // 因为 value 在进行数据填充时, 是值传递
            // 如果不对数据进行拷贝, 会造成数据覆盖问题
            MyBean copyBean = new MyBean();
            try {
                BeanUtils.copyProperties(copyBean, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (copyBean.getTableName().contains("order")) {
                lstOrderData.add(copyBean);
            } else {
                productId2Name.put(key.toString(), copyBean.getProductName());
            }
        }
        // 分离完毕, 进行数据处理
        for (MyBean currData : lstOrderData) {
            currData.setProductName(productId2Name.get(key.toString()));
            // 写数据
            context.write(key, currData);
        }
    }

}
