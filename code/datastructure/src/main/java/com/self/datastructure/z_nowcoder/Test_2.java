package com.self.datastructure.z_nowcoder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author PJ_ZHANG
 * @create 2021-05-18 16:08
 **/
public class Test_2 {

    public static void main(String[] args) {
        // 342
        System.out.println(getSum(5, new int[]{3, 2, 3, 4, 2}, 2));
    }

    public static int getSum(int total, int[] arr, int count) {
        if (total < count * 2) {
            return -1;
        }
        Map<Integer, Object> map = new HashMap<>();
        int[] maxArray = new int[count];
        int[] minArray = new int[count];
        for (int i = 0; i < arr.length; i++) {
            if (map.containsKey(arr[i])) {
                continue;
            }
            // 大于最大数组的最小值进行替换
            if (arr[i] > maxArray[0]) {
                maxArray[0] = arr[i];
                // 数组重排序
                for (int j = 0; j < count - 1; j++) {
                    if (maxArray[j] > maxArray[j + 1]) {
                        int temp = maxArray[j];
                        maxArray[j] = maxArray[j + 1];
                        maxArray[j + 1] = temp;
                    }
                }
            }
            // 小于最大数组的最大值进行替换
            if (minArray[0] == 0) {
                minArray[0] = arr[i];
            } else if (minArray[count - 1] > arr[i]) {
                minArray[count - 1] = arr[i];
            } else {
                continue;
            }
            // 最小数组重排序
            for (int j = 0; j < count - 1; j++) {
                if (minArray[j] > minArray[j + 1]) {
                    int temp = minArray[j];
                    minArray[j] = minArray[j + 1];
                    minArray[j + 1] = temp;
                }
            }
            map.put(arr[i], null);
        }
        // 数组比较
        if (maxArray[0] <= minArray[count - 1]) {
            return -1;
        }
        int  result = 0;
        for (int i = 0; i <count; i++) {
            result += maxArray[i];
            result += minArray[i];
        }
        return result;
    }

}
