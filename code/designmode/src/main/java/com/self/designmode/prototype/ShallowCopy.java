package com.self.designmode.prototype;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.stream.Stream;

/**
 * 浅拷贝
 * * 方法必须实现Cloneable接口, 不然在调用clone()方法时会报异常
 * CloneNotSupportedException  if the object's class does not support the {@code Cloneable} interface
 *
 * * 浅拷贝问题:
 * * 浅拷贝只会对直接对象进行初始化, 如果该对象内部还存在其他引用类型对象, 则不会进行初始化
 * * 此时会将内部引用类型的地址, 直接赋值给新创建的外部对象
 * * 因为地址没有变化, 如果此时对原对象的该内部引用进行修改, 会关联修改现有对象
 * @author PJ_ZHANG
 * @create 2020-07-24 17:12
 **/
@Getter
@Setter
public class ShallowCopy implements Cloneable, Serializable {
    private String name;
    private String addr;
    private ShallowCopy inner;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public ShallowCopy deepCopy() {
        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            // 写对象到内存中
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
            // 从内存中读对象
            byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object copyObject = objectInputStream.readObject();
            return (ShallowCopy) copyObject;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                byteArrayOutputStream.close();
                objectOutputStream.close();
                byteArrayInputStream.close();
                objectOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
