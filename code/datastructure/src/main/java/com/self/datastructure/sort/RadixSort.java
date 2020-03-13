package com.self.datastructure.sort;

/**
 * 基数排序
 *
 * @author PJ_ZHANG
 * @create 2020-03-13 10:24
 **/
public class RadixSort {

    public static void main(String[] args) {
//        int[] array = {45, 832, 456, 76, 32, 17, 89, 456, 56};
        // 10万个数测试, 52ms
        // 100万测试, 208ms
        // 1000万测试, 1265ms
        // 1E测试, 8609ms, -Xmx9000m(4096M没够用)
        int[] array = new int[100000000];
        for (int i = 0; i < 100000000; i++) {
            array[i] = (int) (Math.random() * 8000000);
        }
        long startTime = System.currentTimeMillis();
        radixSort(array);
//        System.out.println(Arrays.toString(array));
        System.out.println("cast time : " + (System.currentTimeMillis() - startTime));
    }

    /**
     * 基数排序基本流程
     * * 初始化一个二维数组, 第一维表示0-9的10个元素桶, 第二维表示落到桶中的数据
     * * 初始化一个一维数组, 下标表示0-9是个数字, 值表示落到桶中数据的数量
     * * 对要排序的数组依次从个位开始截取处理, 按个位数据落到对应的二维桶中, 并用一维数组进行计数
     * * 一轮位数处理完成后, 从二维数据中依次取出所有数据, 对原数据进行覆盖
     * * 每一轮处理完成后, 记得对一维数据进行置空
     * @param array
     */
    private static void radixSort(int[] array) {

        // 二维数组存储数据
        int[][] dataArray = new int[10][array.length];
        // 一维数据计数
        int[] countArray = new int[10];

        int maxCount = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > maxCount) {
                maxCount = array[i];
            }
        }

        for (int i = 0, round = 1; i < (maxCount + "").length(); i++, round *= 10) {
            for (int j = 0; j < array.length; j++) {
                // 获取位数值
                int data = array[j] / round  % 10;
                // 存储当前值到二维数据
                // 并对一维数据数据统计值递增
                dataArray[data][countArray[data]++] = array[j];
            }
            int index = 0;
            // 先从一维数据中获取到存在有效数据的二维数据部分
            for (int countIndex = 0; countIndex < countArray.length; countIndex++) {
                if (countArray[countIndex] == 0) continue;
                // 从二维数据获取到有效数据, 存储到原数组中
                for (int dataIndex = 0; dataIndex < countArray[countIndex]; dataIndex++) {
                    array[index++] = dataArray[countIndex][dataIndex];
                }
                // 统计数组处理完成后, 对统计数量置空
                countArray[countIndex] = 0;
            }
        }


    }

}
