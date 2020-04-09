package com.self.datastructure.tree.huffmancode;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件压缩_赫夫曼编码
 *
 * @author PJ_ZHANG
 * @create 2020-04-08 11:24
 **/
public class FileHuffmanCode {

    public static void main(String[] args) {
        // 文件压缩
        // 首先压缩方式与文本基本一致, 读取到文件的所有字节码后
        // 构造赫夫曼树, 生成路径映射
        // 构造二进制数字串之后转为byte[]数组
        // 写出byte[]数组和路径映射到文件中, 该文件即为压缩文件
        compress("F:\\123.bmp", "F:\\123.zip");
        System.out.println("压缩完成...");

        // 文件解压
        // 解压是先从第一布的压缩文件路径中读取到写出的byte[]数组和路径映射
        // 之后对byte[]数组进行二进制数字串转换再多最后的源文件字节数组转换
        // 最后写出字节数组到目标文件中, 视为对压缩文件的解压
        decompress("F:\\123.zip", "F:\\1234.bmp");
        System.out.println("解压完成...");
    }

    /**
     * 文件解压
     *
     * @param srcFilePath
     * @param desFilePath
     */
    private static void decompress(String srcFilePath, String desFilePath) {
        FileInputStream is = null;
        ObjectInputStream ois = null;
        FileOutputStream os = null;

        try {
            is = new FileInputStream(srcFilePath);
            ois = new ObjectInputStream(is);
            // 按顺序读取赫夫曼码映射和赫夫曼编码转换后的字节数组
            Map<Byte, String> pathMap = (Map<Byte, String>) ois.readObject();
            byte[] bytes = (byte[]) ois.readObject();
            // 解压为真是的字节数组
            byte[] needBytes = ContentHuffmanCode.decode(bytes, pathMap);
            // 写出去
            os = new FileOutputStream(desFilePath);
            os.write(needBytes);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                ois.close();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 文件压缩
     *
     * @param srcFilePath 文件源路径
     * @param desFilePath 文件目标路径
     */
    private static void compress(String srcFilePath, String desFilePath) {
        // 初始化输入输出流
        FileInputStream is = null;
        FileOutputStream os = null;
        ObjectOutputStream oos = null;

        try {
            // 读取文件字节码
            is = new FileInputStream(srcFilePath);
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            // 构造赫夫曼编码路径映射及转换后的字节码
            Map<Byte, String> pathMap = new HashMap<>(16);
            byte[] huffmanBytes = ContentHuffmanCode.encode(bytes, pathMap);
            // 写数据到目标文件中, 作为压缩文件
            os = new FileOutputStream(desFilePath);
            oos = new ObjectOutputStream(os);
            oos.writeObject(pathMap);
            oos.writeObject(huffmanBytes);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                os.close();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
