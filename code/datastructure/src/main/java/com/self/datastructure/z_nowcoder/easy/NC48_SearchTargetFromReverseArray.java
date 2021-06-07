package com.self.datastructure.z_nowcoder.easy;

/**
 * NC48: 在旋转过的有序数组中寻找目标值
 * 给定一个整数数组nums，按升序排序，数组中的元素各不相同。
 * nums数组在传递给search函数之前，会在预先未知的某个下标 t（0 <= t <= nums.length-1）上进行旋转，
 * 让数组变为[nums[t],nums[t+1], ..., nums[nums.length-1], nums[0], nums[1], ..., nums[t-1]]。
 * 比如，数组[0,2,4,6,8,10]在下标2处旋转之后变为[6,8,10,0,2,4]
 * 现在给定一个旋转后的数组nums和一个整数target，
 * * 请你查找这个数组是不是存在这个target，
 * * 如果存在，那么返回它的下标，如果不存在，返回-1
 *
 * @author PJ_ZHANG
 * @create 2021-04-28 11:02
 **/
public class NC48_SearchTargetFromReverseArray {

    public static void main(String[] args) {
        System.out.println(search(new int[]{1}, 1));
    }

    /**
     * * 判断target大小, 与nums的两端进行比较
     * * 如果target大于nums[0], 则从头开始比较, 到nums[i+1] < nums[i] 结束
     * * 找到返回索引值, 找不到返回-1
     * * 如果target小于nums[length - 1], 则从尾部开始比较, 到nums[i - 1] > nums[i] 结束
     * * 找到返回索引值, 找不到返回-1
     * @param nums
     * @param target
     * @return
     */
    public static int search (int[] nums, int target) {
        // 大于左侧最小值, 则从左侧开始比较, 比到nums[i] > nums[i + 1] 结束
        if (target >= nums[0]) {
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] == target) {
                    return i;
                }
                if (i == nums.length - 1 || nums[i] > nums[i + 1]) {
                    return -1;
                }
            }
        }
        // 小于右侧最大值, 从右侧开始比较, 比到nums[i - 1] > nums[i] 结束
        if (target <= nums[nums.length - 1]) {
            for (int i = nums.length - 1; i >= 0; i--) {
                if (nums[i] == target) {
                    return i;
                }
                if (i == 0 || nums[i - 1] > nums[i]) {
                    return -1;
                }
            }
        }
        return -1;
    }

}
