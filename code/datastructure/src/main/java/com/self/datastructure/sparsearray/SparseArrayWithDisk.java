package com.self.datastructure.sparsearray;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 稀疏数组
 * @author LiYanBin
 * @create 2020-01-06 16:07
 **/
public class SparseArrayWithDisk {

    public static void main(String[] args) throws  Exception {
        // 初始化棋盘数组
        int[][] array = new int[11][11];
        // 添加可旗子, 1为黑旗 2位白旗
        array[0][1] = 1;
        array[1][2] = 2;
        array[7][8] = 2;
        System.out.println("初始化数据.........");
        printArray(array);
        // 数组转换为稀疏数组
        System.out.println("转换的稀疏数组为........");
        int[][] sparseArray = arrayToSparse(array);
        printArray(sparseArray);
        // 将稀疏数据写到磁盘
        writeSparseArray(sparseArray);
        // 从磁盘读取稀疏数据
        sparseArray = readSparseArray();
        // 稀疏数组转换为数据
        System.out.println("稀疏数组转换的数组为........");
        int[][] newArray = sparseToArray(sparseArray);
        printArray(newArray);
    }

    private static int[][] readSparseArray() {
        try {
            FileInputStream fileInputStream = new FileInputStream("E:\\1.txt");
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            String json = new String(bytes);
            JSONArray jsonArray = JSON.parseArray(json);
            int[][] sparseAarray = new int[jsonArray.size()][3];
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONArray currJsonArray = JSON.parseArray(jsonArray.get(i).toString());
                sparseAarray[i][0] = Integer.parseInt(currJsonArray.get(0).toString());
                sparseAarray[i][1] = Integer.parseInt(currJsonArray.get(1).toString());
                sparseAarray[i][2] = Integer.parseInt(currJsonArray.get(2).toString());
            }
            return sparseAarray;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeSparseArray(int[][] sparseArray) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("E:\\1.txt");
            fileOutputStream.write(JSON.toJSONString(sparseArray).getBytes());
            fileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 数组转稀疏数组
     */
    public static int[][] arrayToSparse(int[][] array) {
        // 遍历二维数组, 获取有效数据
        int sum = 0;
        for (int[] currArray : array) {
            for (int data : currArray) {
                if (data != 0) {
                    sum++;
                }
            }
        }
        // 初始化稀疏数组
        int[][] sparseArray = new int[sum + 1][3];
        // 填充第一行, 即统计行
        sparseArray[0][0] = 11;
        sparseArray[0][1] = 11;
        sparseArray[0][2] = sum;
        // 填充后续行, 即元素行
        // 填充稀疏数组
        int count = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (0 != array[i][j]) {
                    count++;
                    sparseArray[count][0] = i;
                    sparseArray[count][1] = j;
                    sparseArray[count][2] = array[i][j];
                }
            }
        }
        return sparseArray;
    }

    /**
     * 稀疏数组转数组
     */
    public static int[][] sparseToArray(int[][] sparseArray) {
        // 解析稀疏数组第一行, 初始化二维数组
        int[][] array = new int[sparseArray[0][0]][sparseArray[0][1]];
        int sum = sparseArray[0][2];
        for (int i = 1; i <= sum; i++) {
            // 稀疏数组二维三列,
            // 第一列表示横坐标
            // 第二列表示纵坐标
            // 第三列表示值
            array[sparseArray[i][0]][sparseArray[i][1]] = sparseArray[i][2];
        }
        return array;
    }

    public static void printArray(int[][] array) {
        for (int[] currArray : array) {
            for (int data : currArray) {
                System.out.print(data + "\t");
            }
            System.out.println();
        }
    }

}
