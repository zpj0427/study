package com.hadoop.mapreduce.mapjoin;

import com.hadoop.mapreduce.reducejoin.MyBean;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义Mapper
 * 入参: 行号, 行数据
 * 出参: 商品ID, 自定义列表
 */
public class JoinMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    private Text outKey = new Text();

    private Map<String, String> key2Name = new HashMap<>();

    /**
     * 数据初始化, 取表名称, 对应的表关键字
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);
        // 取预加载文件
        URI[] cacheFiles = context.getCacheFiles();
        Path path = new Path(cacheFiles[0]);
        FileSystem fileSystem = FileSystem.get(context.getConfiguration());
        FSDataInputStream inputStream = fileSystem.open(path);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String line = null;
        // 解析数据, 并对数据进行缓存
        while (null != (line = bufferedReader.readLine())) {
            String[] arr = line.split(" ", -1);
            key2Name.put(arr[0], arr[1]);
        }
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] arr = line.split(" ", -1);
        outKey.set(key2Name.get(arr[0]) + " " + arr[1]);
        context.write(outKey, NullWritable.get());
    }

}
