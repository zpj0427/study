package com.hadoop.mapreduce.sortpartition;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 自定义Hadoop对象, 需要满足Hadoop排序要求, 实现 WritableComparable 接口
 * WritableComparable<T> extends Writable, Comparable<T>
 * 能同时实现序列化和排序两个需求
 *
 * @author PJ_ZHANG
 * @create 2021-05-28 14:40
 **/
public class SelfDomain implements WritableComparable<SelfDomain> {

    /**
     * 上行流量
     */
    private long uploadBytes;

    /**
     * 下行流量
     */
    private long downloadBytes;

    /**
     * 汇总流量
     */
    private long sumBytes;

    public long getUploadBytes() {
        return uploadBytes;
    }

    public void setUploadBytes(long uploadBytes) {
        this.uploadBytes = uploadBytes;
    }

    public long getDownloadBytes() {
        return downloadBytes;
    }

    public void setDownloadBytes(long downloadBytes) {
        this.downloadBytes = downloadBytes;
    }

    public long getSumBytes() {
        return sumBytes;
    }

    public void setSumBytes(long sumBytes) {
        this.sumBytes = sumBytes;
    }

    /**
     * 序列化顺序无所谓, 可以进行自定义
     *
     * @param out
     * @throws IOException
     */
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeLong(uploadBytes);
        out.writeLong(downloadBytes);
        out.writeLong(sumBytes);
    }

    /**
     * 反序列化顺序必须严格与序列化顺序一致, 不然取数据可能会有问题
     * @param in
     * @throws IOException
     */
    @Override
    public void readFields(DataInput in) throws IOException {
        uploadBytes = in.readLong();
        downloadBytes = in.readLong();
        sumBytes = in.readLong();
    }

    @Override
    public String toString() {
        return uploadBytes + "\t" + downloadBytes + "\t" + sumBytes;
    }

    @Override
    public int compareTo(SelfDomain o) {
        long result = o.sumBytes - this.sumBytes;
        if (0 == result) {
            result = o.downloadBytes - this.downloadBytes;
            if (0== result) {
                result = o.uploadBytes - this.uploadBytes;
            }
        }
        return (int) result;
    }
}
