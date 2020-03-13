package com.self.datastructure.search;

/**
 * 线性查找
 *
 * @author PJ_ZHANG
 * @create 2020-03-13 15:18
 **/
public class SeqSearch {

    public static void main(String[] args) {
        int[] array = {45, 832, 456, 76, 32, 17, 89, 456, 56};
        System.out.println(seqSearch(array, 56));
    }

    public static int seqSearch(int[] array, int target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }

}
