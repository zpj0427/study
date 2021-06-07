package com.self.datastructure.z_nowcoder;

/**
 * @author PJ_ZHANG
 * @create 2021-05-18 15:48
 **/
public class Test_1 {

    public static void main(String[] args) {
        System.out.println(sum(new int[] {1, 2, 3, 4, 2}, 20));
    }

    public static int sum(int[] arr, int target) {
        int maxCount = -1;
        for (int i = 0; i < arr.length; i++) {
            int currTarget = arr[i];
            int count = 1;
            for (int j = i + 1; j < arr.length; j++) {
                currTarget += arr[j];
                count++;
                if (currTarget == target) {
                    maxCount = maxCount > count ? maxCount : count;
                } else if (currTarget > target) {
                    break;
                }
            }
        }
        return maxCount;
    }

}
