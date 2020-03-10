package com.self.datastructure.sort;

/**
 * 快速排序
 *
 * @author PJ_ZHANG
 * @create 2020-03-06 15:00
 **/
public class QuickSort {

    private static int count = 0;

    public static void main(String[] args) {
//        int[] array = {6, 8, 9, 1, 4, 3, 5, 6, 8};
        // 10万个数测试, 44ms
        // 100万测试, 193ms
        // 1000万测试, 2224ms
        int[] array = new int[10000000];
        for (int i = 0; i < 10000000; i++) {
            array[i] = (int) (Math.random() * 8000000);
        }
        long startTime = System.currentTimeMillis();
        quickSort(array, 0, array.length - 1);
//        System.out.println(Arrays.toString(array));
        System.out.println("cast time : " + (System.currentTimeMillis() - startTime));
        System.out.println("调用次数: " + count);
    }

    // 快速排序
    // 先从数组中随机取一个参考值,
    // 分别从数组两边(left, right)开始取数据进行比较
    // 如果left取到的数据大于基准数据, right取到的数据小于基准数据, 则进行交换
    // 交换完成后, 对两侧数据分别与参考值比较, 如果与参考值相等, 则对侧进1
    // 一次遍历完成后, 以参考值为中点, 左侧数据小于该值, 右侧数据大于该值
    // 继续递归左右两边进行同样处理, 知道左右两侧数据数量足够下, 则数组有序
    private static void quickSort(int[] array, int left, int right) {
        count++;
        int l = left;
        int r = right;
        // 取一个基本值
        int baseData = array[l];
        // 从两边开始进行判断
        while (l < r) {
            // 去左侧大于等于基本值的数据
            while (array[l] < baseData) {
                l++;
            }
            // 取右侧小于等于基本值的数据
            while (array[r] > baseData) {
                r--;
            }
            // 如果此时l大于等于r, 说明一趟已经比较完成, 直接退出
            if (l >= r) {
                break;
            }
            // 进行数据交换
            int temp = array[l];
            array[l] = array[r];
            array[r] = temp;
            // 因为上面已经进行过交换
            // 如果l侧数据与基础数据相等,则r测数据一定大于基础数据, r--
            if (array[l] == baseData) {
                r--;
            }
            // 如果r侧数据与基础数据相等,则l测数据一定小于基础数据, l++
            if (array[r] == baseData) {
                l++;
            }
        }
        // 出循环后, 说明一个基础值的数据已经比较完毕, 此时如果l = r, 则错开数据
        // 两侧分别进1
        // 如果不添加该部分, 可能会栈溢出
        if (l == r) {
            l++;
            r--;
        }
        // 以当前基准值为中点, 左侧为小于该值的数据, 右侧为大于该值的数据, 递归进行两侧处理, 知道数据有序
        if (left < r) {
            quickSort(array, left, r);
        }
        if (l < right) {
            quickSort(array, l, right);
        }
    }

}
