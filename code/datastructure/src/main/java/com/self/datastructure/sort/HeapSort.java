package com.self.datastructure.sort;

import java.util.Arrays;

/**
 * 堆排序
 *
 * @author PJ_ZHANG
 * @create 2020-03-25 9:39
 **/
public class HeapSort {

    public static void main(String[] args) {
        // int array[] = {4, 6, 8, 5, 9, -1, 3, 1, 20, 2, 7, 30, 5, 8, 6, 3, 1};
        // 10万个数测试,  23ms
        // 100万, 291ms
        // 1000万, 3691ms
        int[] array = new int[10000000];
        for (int i = 0; i < 10000000; i++) {
            array[i] = (int) (Math.random() * 8000000);
        }
        long startTime = System.currentTimeMillis();
        heapSort(array);
//        System.out.println(Arrays.toString(array));
        System.out.println("cast time : " + (System.currentTimeMillis() - startTime));
    }

    /**
     * 堆排序:
     * 堆排序基本思想为顺序存储二叉树
     * 对某一个存在字节点的节点来说, index从0开始
     *  * k = 2 * index + 1
     *  * rightIndex = 2 * index + 2
     * 同样, 对某一个存在父节点的节点来说
     *  * parentIndex = (index - 1) / 2
     * 在整个顺序存储二叉树中, 最后一个非叶子节点的索引为
     *  * index = arr.length / 2 - 1
     * 以上几组公式是堆排序的基础
     *
     * 堆排序基本规则
     * * 先将数组转换为大顶堆或者小顶堆的格式, 以大顶堆为例
     * * 转换大小顶堆时, 需要从最后一个非叶子节点向前依次比较, 保证父节点大于叶子节点, 直到root节点
     * * 此时基本的大顶堆已经转换完成, 转换完成基本大顶堆是后续处理的基础
     * * 此时root节点肯定是数组中的最大元素, 将root元素与数组的最后一个元素替换
     * * 将原数组长度改为length - 1, 用剩余部分继续重组大顶堆
     * * 因为基本顶堆已经形成, 此时大顶堆只是顶层元素冲突, 只需要对顶层元素持续下沉到合适的位置, 并将大数据上升即可
     * * 以此类推, 直到所以数组元素移到右侧, 则对排序完成
     *
     * @param array
     */
    public static void heapSort(int[] array) {
        // 构造初始化的大顶堆
        // int i = array.length / 2 - 1: 表示拿到最后一个有效非子节点
        for (int i = array.length / 2 - 1; i >= 0; i--) {
            adjustHeap(array, i, array.length);
        }

        for (int i = array.length - 1; i >= 0; i--) {
            // 大顶堆构造完成后, 此时顶层元素, 即第一个元素为该数组端最大值, 与最后一个值交换
            int max = array[0];
            array[0] = array[i];
            array[i] = max;
            // 此时基本大顶堆结构没乱, 但是root节点值为较小值, 只需要对root节点下沉到合适的位置
            // 数组长度为i
            adjustHeap(array, 0, i);
        }
    }

    /**
     * 构造大顶堆
     * @param array 原始数组
     * @param index 需要处理的数据索引
     * @param length 需要处理的数组长度
     */
    public static void adjustHeap(int[] array, int index, int length) {
        // 存储临时值, 进行最后值替换
        int temp = array[index];

        // 根据index节点索引获取到元素的左侧节点索引
        // 一次处理完成后, 如果存在子节点大于该节点, 则将该位置修改为子节点的位置
        // k = (k * 2 + 1) 即将k替换为左侧节点, 继续下沉判断
        for (int k = index * 2 + 1; k < length; k = (k * 2 + 1)) {
            // 此处找到左右节点较大的元素
            if (k + 1 < length && array[k] < array[k + 1]) {
                k++;
            }
            // 元素大于目标值, 直接将目标值换位较大的节点
            if (array[k] > temp) {
                // 此处替换后, 当前节点与子节点的值一致, 为之前子节点的值, 被覆盖的值在temp中存储
                array[index] = array[k];
                // 将传递的index参数继续往下推, 与较大节点的子节点继续进行匹配, 判断是否继续下推
                // 此处注意, 持续覆盖值后, index的位置一致被k值修改下推, 最后值就是最初指定的数据需要下沉的位置
                index = k;
            } else {
                break;
            }
        }
        // 在循环里面处理完成后, 将temp下沉到合适的位置
        array[index] = temp;
    }

}
