package com.self.datastructure.search;

import java.util.Arrays;

/**
 * 斐波拉契查找法
 *
 * @author pj_zhang
 * @create 2020-03-14 21:50
 **/
public class FibratcheSearch {

    public static void main(String[] args) {
        int[] array = {1, 12, 23, 34, 55, 78, 156, 765, 873, 987};
        System.out.println(fibratcheSearch(array, 874));
    }

    /**
     * 斐波拉契查找流程
     * * 先初始一个长度为20的斐波拉契数组备用, 数组长度如果超过fbl[19], 可以进行扩容
     * * 用数组长度去匹配合适的斐波拉契索引值, 作为原始数组斐波拉契查找法中的真实长度
     * * 对原始数组进行拷贝, 拷贝后的长度 = (斐波拉契的索引值 - 1), 多余原始数组部分初始化为0
     * * 对多余部分初始化为0进行修改, 修改为数组的最大值, 保证数组有序
     * * 数据初始化完成后, 继续进行数据查询, 数据查找与二分法基本一致, 只是middle的取值方式不一致
     * * 斐波拉契查找中: middle = left + F[k - 1] - 1;
     * * 其中F数组表示斐波拉契数组, k表示数据匹配到的斐波拉契数组下标, k对应长度即原始数组拷贝后的长度
     * * 根据斐波拉契查找算法补全位后, 原数组长度为 f[k] - 1
     * * 因为 f[k] = f[k - 1] + f[k - 2]
     * * 所以 f[k] - 1 = f[k - 1] + f[k - 2] - 1
     * * 即 f[k] - 1 = f[k - 1] - 1 + 1 + f[k - 2] - 1
     * * f[k - 1] - 1: 表示middle左侧数组长度
     * * 1: 表示middle所在位置
     * * f[k - 2] - 1: 表示middle右侧数组长度
     *
     * @param array 原数组
     * @param target 需要查找的值
     * @return 返回索引
     */
    public static int fibratcheSearch(int[] array, int target) {
        int left = 0; // 左索引
        int right = array.length - 1; // 右索引
        // 数组长度匹配到的斐波拉契数组下标
        // 该下标对应值为拷贝后的数组长度
        int k = 0;
        // 初始化斐波拉契数组
        int[] fbl = initFbl(20);
        // 用数组长度匹配到斐波拉契数组的对应元素, 比如数组长度为7, 则匹配8; 为10, 匹配13; 依次类推
        // 简单斐波拉契数据: {1, 1, 2, 3, 5, 8, 13, 21}, 即从1开始, 后一个数为前两个数之和
        for (;fbl[k] - 1 < array.length;) {
            // 这部分可以添加扩容逻辑,
            // 20表示初始化长度, 如果k为20依旧小于, 则进行扩容,
            // 扩容可以以array长度进行算法匹配, 求出大概索引位置进行扩容
            // 也可以类似于集合扩容, 进行1.5或者2倍扩容
            k++;
        }
        // 拷贝原数组为斐波拉契查找需要的长度
        int[] temp = Arrays.copyOf(array, fbl[k] - 1);
        // 数组长度增加后, 增加部分数据值初始化为0, 修改值统一为最大值, 保证数组有序
        for (int i = right + 1; i < temp.length; i++) {
            temp[i] = temp[right];
        }

        // 原数组和斐波拉契数组全部初始化完成后, 可以进行数据查找
        // 获取到middle值: middle = left + F[k - 1] - 1;
        for (;left <= right;) {
            // fbl[k]表示当前数组的长度, 如果已经循环多次, 依旧表示查找区间的数组长度
            // 例: 数组长度为13, fbl[k]=13, k=6, left=0, 则middle=7,
            //     此时向左继续查找, 则right=6, k=5, fbl[k]=7;
            // 对于斐波拉契查找法, 中值索引的选择就是以斐波拉契数组的前一个数为基本参考
            // 因此, 此时 midlle 取值就是以fbl[k - 1]作为基本参考
            // 以斐波拉契数组的组成方式,
            //  * middle左侧的数组长度是fbl[k - 1] - 1, 中值索引参考为fbl[k - 1 - 1]
            //  * middle右侧的数组长度是fbl[k - 2] - 1, 中值索引参考为fbl[k - 2 - 1]
            int middle = left + fbl[k - 1] - 1;
            if (temp[middle] > target) { // 向左继续找
                // 如果中值索引对应值小于目标值, 则向左侧继续寻找
                // 此时右侧索引变成中值索引的左侧索引
                right = middle - 1;
                // 当前循环没有匹配到, 且匹配到左侧, 需要进行下一轮循环继续匹配
                // 则下一轮循环的middle就是以fbl[k - 1]为完整数据进行求中值处理
                // 则对于左侧 middle 的参考系为fbl[k - 1 - 1]
                // 所以此时k应该减1
                k--;
            } else if (temp[middle] < target) { // 向右继续找
                // 如果中值索引对应值大于目标值, 则向侧继续寻找
                // 此时左侧索引变为中值索引的右侧索引
                left = middle + 1;
                // 当前没有匹配到, 且匹配到右侧, 需要进行下一轮循环匹配
                // 此时右侧数组的长度为fbl[k - 2]
                // 对于右侧数组来说, 中值索引参考应为fbl[k - 2]的前一个数即fbl[k - 1 - 2]
                // 此时k应该减2
                k -= 2;
            } else { // 相等, 返回索引
                return middle > right ? right : middle;
            }
        }
        return -1;
    }

    /**
     * 初始化斐波拉契数组
     *
     * @return
     */
    private static int[] initFbl(int size) {
        int[] array = new int[size];
        array[0] = 1;
        array[1] = 1;
        for (int i = 2; i < size; i++) {
            array[i] = array[i - 1] + array[i - 2];
        }
        return array;
    }

}
