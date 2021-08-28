package com.hadoop.mapreduce.reducejoin;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 自定义输出类
 */
public class MyBean implements Writable {

    private String orderId;

    private Integer productCount;

    private String productName;

    private String tableName;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(orderId);
        out.writeInt(productCount);
        out.writeUTF(productName);
        out.writeUTF(tableName);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        orderId = in.readUTF();
        productCount = in.readInt();
        productName = in.readUTF();
        tableName = in.readUTF();
    }

    /**
     * 重写 toString() 方法, 只输出重点字段
     * @return
     */
    @Override
    public String toString() {
        return "orderId=" + orderId + ",\t productCount=" + productCount + ",\t productName=" + productName;
    }

}
