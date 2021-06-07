# 牛客网刷题：写的越多说明越不会

# 1，入门题目

## 1.1，NC103_反转字符串

### 1.1.1，题目描述

> 写出一个程序，接受一个字符串，然后输出该字符串反转后的字符串。（字符串长度不超过1000）
>
> 
>
> 输入：“abcd”
>
> 输出：“dcba”

### 1.1.2，代码题解

```java
package com.self.datastructure.z_nowcoder;

/**
 * NC103：反转字符串
 * 写出一个程序，接受一个字符串，然后输出该字符串反转后的字符串。（字符串长度不超过1000）
 *
 * @author PJ_ZHANG
 * @create 2021-04-22 9:42
 **/
public class NC103_ReverseStr {

    public static void main(String[] args) {
        System.out.println(reverseStr_1("abcd"));
        System.out.println(reverseStr_2("abcd"));
        System.out.println(reverseStr_3("abcd"));
    }

    /**
     * 第三种方式，调用API
     * @param str
     * @return
     */
    public static String reverseStr_3(String str) {
        return new StringBuffer(str).reverse().toString();
    }

    /**
     * 第二种方式，位置交换
     * * 将字符串先改为字符数组：如 "abcd" -> ['a', 'b', 'c', 'd']
     * * 然后对字符数组的第一个元素和最后一个元素进行交换, 第二个元素和倒数第二个元素进行交换
     * * 直到进行到 < 中间索引, 则停止交换(偶数位全部交换, 奇数位中间索引不动)
     * * 第0位和第 len - 1位交换, len - 0 - 1
     * * 第1位和第 len - 2位交换, len - 1 - 1
     * * 第2位和第 len - 3位交换, len - 2 - 1
     * * 第i位和第 len - i - 1位交换
     * * 最后重组字符串完成反转
     *
     * @param str
     * @return
     */
    public static String reverseStr_2(String str) {
        if (null == str || str.length() == 0) {
            return str;
        }
        // 完成交换
        char[] array = str.toCharArray();
        for (int i = 0; i < array.length / 2; i++) {
            char temp = array[array.length - i - 1];
            array[array.length - i - 1] = array[i];
            array[i] = temp;
        }
        // 重组字符串
        String reverseStr = "";
        for (char c : array) {
            reverseStr += c;
        }
        return reverseStr;
    }

    /**
     * 第一种方式反转字符串：直接倒叙输出
     * 从最后一个字符开始遍历字符串，并追加到一个新的字符串后面，完成字符串反转
     *
     * @param str
     * @return
     */
    public static String reverseStr_1(String str) {
        if (null == str || str.length() == 0) {
            return str;
        }
        String reverseStr = "";
        // 反转输出
        for (int i = str.length() - 1; i >= 0; i--) {
            reverseStr += str.charAt(i);
        }
        return reverseStr;
    }

}
```

## 1.2，●NC38_螺旋矩阵

### 1.2.1，题目描述

> 给定一个m x n大小的矩阵（m行，n列），按螺旋的顺序返回矩阵中的所有元素
>
>  
>
> 输入：[[1,2,3],[4,5,6],[7,8,9]]
>
> 输出：[1,2,3,6,9,8,7,4,5]

### 1.2.2，图解

![1619061665572](E:\gitrepository\study\note\image\刷题_nowcoder\1619061665572.png)

### 1.2.3，代码题解

```java
package com.self.datastructure.z_nowcoder;

import java.util.ArrayList;

/**
 * NC38: 螺旋矩阵
 * 给定一个m x n大小的矩阵（m行，n列），按螺旋的顺序返回矩阵中的所有元素。
 * 输入: [[1,2,3],[4,5,6],[7,8,9]]
 * 输出: [1,2,3,6,9,8,7,4,5]
 *
 * 输入数组如:
 * 1 2 3
 * 4 5 6
 * 7 8 9
 *
 * 螺旋输出: 即以[0, 0]位置为起点, 以横轴为初始方向, 遇边右转的行进方式, 已经访问的元素不再多次访问
 * 即螺旋输出顺序:
 * 1 -> 2 -> 3
 *           |
 * 4 -> 5    6
 * |         |
 * 7 <- 8 <- 9
 * 最终输出结果为: 1, 2, 3, 6, 9, 8, 7, 4, 5
 * @author PJ_ZHANG
 * @create 2021-04-22 11:00
 **/
public class NC38_ScrewArray {

    public static void main(String[] args) {
        System.out.println(screwArray(new int[][] {{1, 2, 3},{4, 5, 6},{7, 8, 9}}));
        System.out.println(screwArray(new int[][] {{2,3}}));
    }

    /**
     * * 以螺旋的一个圈为一次循环,
     * * 以一维数组为纵坐标, 二维数组为横坐标时
     * * 在这个圈中会完成左, 下, 右, 上四个步骤,
     * * 左: 纵坐标不变, 横坐标递增(当前二维数组操作)
     *       执行完成后进入下一个二维数组, 即横坐标加1
     * * 下: 横坐标不变, 纵坐标递增(固定二维数组索引, 在一维数组切换)
     *       执行完成后进入前一个索引位置, 即纵坐标减1
     * * 左: 纵坐标不变, 横坐标递减(当前二维数组操作)
     *       执行完成后进入前一个二维数组, 即横坐标减1
     * * 上: 横坐标不变, 纵坐标递减(固定二维数组索引, 在一维数组间切换)
     *       执行完成后进入后一个索引位置, 即纵坐标+1
     *
     * @param matrix
     * @return
     */
    public static ArrayList<Integer> screwArray(int[][] matrix) {
        ArrayList<Integer> lstData = new ArrayList<>();
        if (matrix.length == 0) {
            return lstData;
        }
        // 定义开始的纵横坐标
        int startX = 0, startY = 0;
        // 定义结束的纵横坐标
        int endX = matrix.length - 1, endY = matrix[0].length - 1;
        // 当startX <= endX, startY <= endY时, 说明还存在未处理完的数据, 循环继续
        // 考虑一行或者一列的情况
        for (;startX <= endX && startY <= endY;) {
            // 向右: 纵坐标不变, 横坐标递增
            for (int i = startY; i <= endY; i++) {
                lstData.add(matrix[startX][i]);
            }
            startX++;

            // 向下: 横坐标不变, 纵坐标递减
            for (int i = startX; i <= endX; i++) {
                lstData.add(matrix[i][endY]);
            }
            endY--;

            // 开始横坐标小于结束横坐标, 说明该点位没有执行过
            if (startX <= endX) {
                // 向左: 纵坐标不变, 横坐标递减(当前二维数组操作)
                for (int i = endY; i >= startY; i--) {
                    lstData.add(matrix[endX][i]);
                }
                endX--;
            }

            // 开始纵坐标小于结束纵坐标, 说明该点位没有执行过
            if (startY <= endY) {
                // 向上: 横坐标不变, 纵坐标递减(固定二维数组索引, 在一维数组间切换)
                for (int i = endX; i >= startX; i--) {
                    lstData.add(matrix[i][startY]);
                }
                startY++;
            }
        }
        return lstData;
    }

}
```

## 1.3，NC65_斐波拉契数列

### 1.3.1，题目描述

> 大家都知道斐波那契数列，现在要求输入一个整数n，请你输出斐波那契数列的第n项（从0开始，第0项为0，第1项是1）
>
>  
>
> 输入：4
>
> 输出：3

### 1.3.2，斐波拉契数列

> 斐波拉契数列是一种数组类型：数字第一位为0，第二位为1，后续索引位等于前两位索引为之和
>
> [0, 1, 1, 2, 3, 5, 8, 13, 21, 34...]

### 1.3.3，代码题解

```java
package com.self.datastructure.z_nowcoder;

/**
 * NC65_斐波拉契数列
 * 大家都知道斐波那契数列，现在要求输入一个整数n，请你输出斐波那契数列的第n项（从0开始，第0项为0，第1项是1）。39n≤39
 * 输入: 4
 * 输出: 3
 * @author PJ_ZHANG
 * @create 2021-04-22 10:42
 **/
public class NC65_Fibonacci {

    public static void main(String[] args) {
        System.out.println(fibonacci_1(7));
        System.out.println(fibonacci_2(7));
    }

    /**
     * 斐波拉契数列: 第一位为0, 第二位为1, 后续每一位为前两位之和的数组
     * [0, 1, 1, 2, 3, 5, 8, 13, 21, 34...]
     * 按题目要求是取 n 索引出的数据: arr[4] = 3
     * * 先构建长度为 n + 1 的数组, 并按数列规则填充数列
     * * 对0位填0, 第1位填1, 后续每一位为前两位之和
     * * 数组构建完成后, 直接取第 n 位 索引的数据即可
     * @param n
     * @return
     */
    public static int fibonacci_1(int n) {
        if (n == 0 || n == 1)
            return n;
        int[] arr = new int[n + 1];
        arr[1] = 1;
        for (int i = 2; i <= n; i++) {
            arr[i] = arr[i - 1] + arr[i - 2];
        }
        return arr[n];
    }

    /**
     * 上一种方法空间复杂度太高, 可以不创建数组, 直接计算完成
     * @param n
     * @return
     */
    public static int fibonacci_2(int n) {
        if (n == 0 || n == 1)
            return n;
        int num1 = 0, num2 = 1;
        int result = 0;
        for (int i = 2; i <= n; i++) {
            result = num1 + num2;
            num1 = num2;
            num2 = result;
        }
        return result;
    }

}
```

## 1.4，NC141_判断回文

### 1.4.1，题目描述

> 给定一个字符串，请编写一个函数判断该字符串是否回文。如果回文请返回true，否则返回false。
> 输入: "absba"
> 输出: true
>
> 输入: "ranko"
> 输出: fasle

### 1.4.2，代码题解

```java
package com.self.datastructure.z_nowcoder;

import java.util.logging.Level;

/**
 * NC141: 判断回文
 * 给定一个字符串，请编写一个函数判断该字符串是否回文。如果回文请返回true，否则返回false。
 * 输入: "absba"
 * 输出: true
 * 输入: "ranko"
 * 输出: fasle
 *
 * * 之前做了一次字符串反转, 可以直接在反转的基础上判断字符串是否相等就行
 * * 在这个基础上, 可以在字符数组进行首位比较的时候直接进行判断
 * * 即第一位和最后一位判断, 第二位和倒数第二位判断
 * * 如果有一组对应不上, 说明不是回文串
 *
 * @author PJ_ZHANG
 * @create 2021-04-23 9:56
 **/
public class NC141_LMStr {

    public static void main(String[] args) {
        System.out.println(judge("abcd"));
        System.out.println(judge("abcdcba"));
    }

    /**
     * 通过字符数组首尾比较实现
     * @param str
     * @return
     */
    public static boolean judge (String str) {
        if (null == str || str.length() <= 1) {
            return true;
        }
        char[] array = str.toCharArray();
        for (int i = 0; i < array.length / 2; i++) {
            if (array[i] != array[array.length - 1 - i]) {
                return false;
            }
        }
        return true;
    }

}
```

## 1.5，NC107_寻找峰值

### 1.5.1，题目描述

> 山峰元素是指其值大于或等于左右相邻值的元素。给定一个输入数组nums，任意两个相邻元素值不相等，数组可能包含多个山峰。找到索引最大的那个山峰元素并返回其索引。
> 输入: [2,4,1,2,7,8,4]
> 输出: 5

### 1.5.2，题目解析

* <font color=red>注意审题：是找到索引最大的那个山峰元素，不是值最大！！！</font>

### 1.5.3，代码题解

```java
package com.self.datastructure.z_nowcoder;

/**
 * NC107: 寻找峰值
 * 山峰元素是指其值大于或等于左右相邻值的元素。
 * 给定一个输入数组nums，任意两个相邻元素值不相等，数组可能包含多个山峰。
 * 找到索引最大的那个山峰元素并返回其索引。
 * 输入: [2,4,1,2,7,8,4]
 * 输出: 5
 *
 * ***** 注意寻找最大的索引, 不是最大值的索引
 *
 * @author PJ_ZHANG
 * @create 2021-04-23 10:14
 **/
public class NC107_SearchPeakValue {

    public static void main(String[] args) {
        System.out.println(slove_1(new int[]{1, 2, 3, 4, 3, 4, 9}));
    }

    /**
     * 第二种题解
     * 从数组的最后一位开始, 用后一位与前一位进行比较
     * 如果前一位小于该值, 则说明该位置是数组的做大索引峰值
     * 如果前一位大于该值, 会继续往前追 (i - 1 > i -> i > i + 1)
     * 追到前一位小于该值, 则说明该值峰值(i - 1 < i)
     * 此时前后数位比较就是(i - 1 < i > i + 1)
     * 因为是从后往前遍历, 所以该索引必然为最大索引
     * 遍历到最开始还没有找到峰值, 则峰值必然为0索引, 说明数组是一个倒序数组
     *
     * @param a
     * @return
     */
    public static int slove_1(int[] a) {
        if (a.length == 1) {
            return 0;
        }
        for (int i = a.length - 1; i > 0; i--) {
            if (a[i] > a[i - 1]) {
                return i;
            }
        }
        return 0;
    }

    /**
     * 暴力解题
     * * 注意题目要求: 取最大索引的峰值, 不是取峰值的最大值
     * * 从1索引出开始, 对每一个数与两边的数进行比较, 均大于说明为峰值
     * * 对该峰值的索引进行记录
     * * 执行完毕后, 对最后一个数进行与前一位的比较, 保证所有数据遍历完成
     * * 最终返回最大索引
     * ***** 这个办法太笨
     *
     * @param a
     * @return
     */
    public static int solve(int[] a) {
        // 对1和2先进行特殊处理
        if (a.length == 1) {
            return 0;
        }
        if (a.length == 2) {
            return a[0] > a[1] ? 0 : 1;
        }
        int maxIndex = -1;
        for (int i = 1; i < a.length - 1; i++) {
            // 找到峰值
            if (a[i] > a[i - 1] && a[i] > a[i + 1]) {
                maxIndex = i;
            }
        }
        // 最后对末尾进行比较
        return a[a.length - 1] > a[a.length - 2] ? a.length - 1 : maxIndex;
    }

}
```

## 1.6，●NC110_旋转数组

### 1.6.1，题目描述

> 一个数组A中存有N（N&gt0）个整数，在不允许使用另外数组的前提下，将每个整数循环向右移M（M>=0）个位置，即将A中的数据由（A0 A1 ……AN-1 ）变换为（AN-M …… AN-1 A0 A1 ……AN-M-1 ）（最后M个数循环移至最前面的M个位置）。如果需要考虑程序移动数据的次数尽量少，要如何设计移动的方法？
>
> 输入：6,2,[1,2,3,4,5,6]
>
> 输出：[5,6,1,2,3,4]

### 1.6.2，题目解析

* <font color=red>不排除 M > N 的情况，所以需要对 M 过大时的情况处理，防止数组越界</font>
* 旋转数组，可以通过三次数组翻转实现：
* 对于一个数组 `{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}`，向右移动5次目标值为 `{5, 6, 7, 8, 9, 0, 1, 2, 3, 4}`
* 首先，进行第一次翻转，进行全数组翻转：`{9, 8, 7, 6, 5, 4, 3, 2, 1, 0}`
* 其次，进行第二次反转，将右移的后半部分，也就是反转后的前半部分进行反转，数组范围[0，M)，翻转结果 `{5, 6, 7, 8, 9, 4, 3, 2, 1, 0}`
* 最后，进行第三次反转，将右移的前半部分，也就是反转后的后半部分进行反转，数组范围[M，N)，反转结果 `{5, 6, 7, 8, 9, 0, 1, 2, 3, 4}`
* 至此完成数组旋转

### 1.6.3，代码题解

```java
package com.self.datastructure.z_nowcoder.accidence;

import java.util.Arrays;

/**
 * NC110: 旋转数组
 * 一个数组A中存有N（N > 0）个整数，在不允许使用另外数组的前提下，将每个整数循环向右移M（M>=0）个位置，
 * 即将A中的数据由（A0 A1 ……AN-1 ）变换为（AN-M …… AN-1 A0 A1 ……AN-M-1 ）
 * （最后M个数循环移至最前面的M个位置）。如果需要考虑程序移动数据的次数尽量少，要如何设计移动的方法？
 *
 * * 对数组进行三次反转
 * * 如数组 {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 向右移动五位变成{5, 6, 7, 8, 9, 0, 1, 2, 3, 4}
 * * 先对全数组进行第一次反转: {9, 8, 7, 6, 5, 4, 3, 2, 1, 0}
 * * 再对 M=5 前的位置进行第二次反转: {5, 6, 7, 8, 9, 4, 3, 2, 1, 0}
 * * 最后对 M=5 后的位置进行第三次反转: {5, 6, 7, 8, 9, 0, 1, 2, 3, 4}
 *
 * @author PJ_ZHANG
 * @create 2021-04-23 10:52
 **/
public class NC110_RevolveArray {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solve(10, 3, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9})));
    }

    public static int[] solve(int n, int m, int[] a) {
        // 对 m 取模, 存在 m > n 的情况, m = n 说明数组不动
        m = m % n;
        // 第一次反转, 全数据反转
        reverse(a, 0, n);
        // 第二次反转, 前半部分反转
        reverse(a, 0, m);
        // 第三次反转, 后半部分反转
        reverse(a, m, n);
        return a;
    }

    public static void reverse(int[] a, int startIndex, int endIndex) {
        for (int i = startIndex; i < (startIndex + endIndex) / 2; i++) {
            int temp = a[i];
            a[i] = a[startIndex + endIndex - 1 - i];
            a[startIndex + endIndex - 1 - i] = temp;
        }
    }

}
```

## 1.7，NC151_最大公约数

### 1.7.1，题目描述

> 求出两个数的最大公约数，如果有一个自然数a能被自然数b整除，则称a为b的倍数，b为a的约数。几个自然数公有的约数，叫做这几个自然数的公约数。公约数中最大的一个公约数，称为这几个自然数的最大公约数。
>
> 输入：3， 6
>
> 输出：3

### 1.7.2，代码题解

```java
package com.self.datastructure.z_nowcoder.accidence;

/**
 * NC151: 最大公约数
 * 求出两个数的最大公约数，
 * 如果有一个自然数a能被自然数b整除，则称a为b的倍数，b为a的约数。
 * 几个自然数公有的约数，叫做这几个自然数的公约数。
 * 公约数中最大的一个公约数，称为这几个自然数的最大公约数。
 * <p>
 * * 先取两个数中的较小值, 以较小值为蓝本进行比较, 最大公约数, 再大没意义
 * * 如果较大值能被较小值整除, 则直接获得公约数为较小值
 * * 如果不能直接整除, 则从一半处开始, 倒序向1处比较
 *
 * @author PJ_ZHANG
 * @create 2021-04-23 11:31
 **/
public class NC151_MaxCommonDivisor {

    public static void main(String[] args) {
        System.out.println(gcd_2(90, 60));
    }

    /**
     * 更相减损法 - 源自《九章算术》
     * 可半者半之，不可半者，副置分母、子之数，以少减多，更相减损，求其等也。以等数约之。
     *
     * 就是比较两个数互相减, 用大的减小的, 把结果赋值给大的, 等到两个数相等了, 就是最大公约数了
     * @param a
     * @param b
     * @return
     */
    public static int gcd_2(int a, int b) {
        if (a % b == 0)
            return a;
        if (b % a == 0)
            return b;
        // 进行神奇的九章算术
        for (; a != b ;) {
            if (a > b) {
                a = a - b;
            }
            if (b > a) {
                b = b - a;
            }
        }
        return a;
    }

    /**
     * 辗转相除法 - 欧几里得算法
     * 用两个数进行取模, 如果余数为0, 表示可以整除, 除数为最大公约数
     * 如果不能整除, 则以除数作为被除数, 以余数作为除数继续整除
     * 直接余数为0, 此时除数即为最大公约数
     * @param a
     * @param b
     * @return
     */
    public static int gcd_1(int a, int b) {
        int temp = 0;
        // 进行神奇的辗转相除
        for (; (temp = a % b) != 0; ) {
            a = b;
            b = temp;
        }
        return b;
    }

    /**
     * 暴力计算法
     * 对较小数取半之后进行计算, 稍微减少点计算时间
     *
     * @param a
     * @param b
     * @return
     */
    public static int gcd(int a, int b) {
        int min = Math.min(a, b);
        int max = Math.max(a, b);
        if (max % min == 0) {
            return min;
        }
        for (int i = min / 2; i > 0; i--) {
            if (min % i == 0 && max % i == 0) {
                return i;
            }
        }
        return 1;
    }

}
```

## 1.8，NC657_最小差值

### 1.8.1，题目描述

> 给你一个数组a*a*，请你求出数组a中任意两个元素间差的绝对值的最小值。(2≤*l**e**n*(*a*)≤103)
>
> 输入：[1,2,4]  输出：1 （|1 - 2| = 1）
>
> 输入：[1,3,1]  输出：0 （|1 - 1| = 0）

### 1.8.2，题目解析

* <font color=red>计算时候注意，无论加减都可能超出 `int` 的范围，可以在计算时暂时提升为 `long` 进行计算，计算完成后再进行 `int` 还原</font>

### 1.8.3，代码题解

```java
package com.self.datastructure.z_nowcoder.accidence;

/**
 * NC657: 最小差值
 * 给你一个数组aa，请你求出数组a中任意两个元素间差的绝对值的最小值。
 * 输入: [1,2,4]
 * 输出: 1 (|1 - 2| = 1)
 * 输入: [1,3,1]
 * 输出: 0 (1 - 1 = 0)
 * @author PJ_ZHANG
 * @create 2021-04-23 15:02
 **/
public class NC657_MinDValue {

    public static void main(String[] args) {
        System.out.println(minDifference(new int[] {-2147483648,0,2147483647}));
    }

    /**
     * 取最小差值的绝对值, 数组任意两个元素之间
     * * 从第一个元素开始, 依次与后续元素进行比较, 取最小差值进行一次记录替换
     * * 如果差值为0, 表示已经最小, 直接退出即可
     *
     * @param a
     * @return
     */
    public static int minDifference (int[] a) {
        // 记录最小差值
        long minData = Integer.MAX_VALUE;
        // 外层循环遍历到倒数第二个数
        for (int i = 0; i < a.length - 1; i++) {
            // 内层循环从完成循环的后一个数开始, 到最后一个数结束
            for (int j = i + 1; j < a.length; j++) {
                // 此处考虑int溢出, 用long暂时处理
                long tempData = Math.abs((long)a[i] - (long)a[j]);
                tempData = tempData < 0 ? Integer.MAX_VALUE : tempData;
                if (tempData < minData) {
                    if (0 == tempData) {
                        return 0;
                    }
                    minData = tempData;
                }
            }
        }
        // 处理完成后, 如果值大于int最大值, 以最大值返回, 或者直接返回该值即可
        return minData > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) minData;
    }

}
```

## 1.9，NC661_热心的牛牛

### 1.9.1，题目描述

> 这一天你跟你的n个朋友一起出去玩，在出门前妈妈给了你k块糖果，你决定把这些糖果的一部分分享给你的朋友们。由于你非常热心，所以你希望你的每一个朋友分到的糖果数量都比你要多（严格意义的多，不能相等）。求你最多能吃到多少糖果？
>
> 输入：2,10  输出：2
>
> 说明：你可以分给你的两个朋友各4个糖果，这样你能吃到2个糖果，这样能保证你的每个朋友的糖果数都比你多，不存在你能吃到3个或者以上糖果的情况
>
> 输入：3,11  输出：2
>
> 你可以分给你的3个朋友各3个糖果，这样你能吃到2个糖果，这样能保证你的每个朋友的糖果数都比你多，不存在你能吃到3个或者以上糖果的情况

### 1.9.2，代码题解

```java
package com.self.datastructure.z_nowcoder.accidence;

/**
 * NC661: 热心的牛牛
 * 这一天你跟你的n个朋友一起出去玩，在出门前妈妈给了你k块糖果，
 * 你决定把这些糖果的一部分分享给你的朋友们。
 * 由于你非常热心，所以你希望你的每一个朋友分到的糖果数量都比你要多（严格意义的多，不能相等）。
 * 求你最多能吃到多少糖果？
 * @author PJ_ZHANG
 * @create 2021-04-23 15:27
 **/
public class NC661_SplitCandy {

    public static void main(String[] args) {
        System.out.println(maximumcandies(2, 10));
    }

    /**
     * 因为要保证我的糖果在绝对少的情况下尽可能的多, 可以分两步处理
     * 假设有13块糖, 有3个小朋友 + 我
     * 第一步: 先对糖果进行平分, 可能会剩下一些糖果, 也可能不会剩下
     * ***** 没人3块, 剩1块
     * 第二部: 分剩下的糖果, 此时可能存在三种情况
     * * 没有剩下糖果: 此时大家糖果都是一样的, 我随便给某个人一个糖果, 我就是绝对少时拥有最多的
     * * 剩下糖果了, 但不够给每个小朋友一块: 剩下1块, 有3个小朋友, 我和其他两个小朋友都拿的3块
     *               我直接给另外一个小朋友一块, 我就是绝对少时拥有最多的
     * * 剩下糖果了, 可以给每个小朋友一块: 如剩下三块, 3个小朋友各拿一块, 我不拿, 我就是绝对少时拥有最多的
     * 综上总结: 只要剩下的糖和小朋友的人数相等, 我就直接拿平均数;
     *           否则, 我就要让出去一块糖
     * @param n 小朋友人数
     * @param k 要分的糖果个数
     * @return
     */
    public static long maximumcandies (long n, long k) {
        long avg = k / (n + 1);
        long other = k % (n + 1);
        if (other == n) {
            return avg;
        }
        return avg - 1;
    }

}
```

## 1.10，NC681_牛牛找数

### 1.10.1，题目描述

> 牛牛有两个数a和b，他想找到一个大于a且为b的倍数的最小整数，只不过他算数没学好，不知道该怎么做，现在他想请你帮忙。给定两个数a和b，返回大于a且为b的倍数的最小整数。
>
> 输入：3,2
>
> 输出：4
>
> 说明：大于3且为2的倍数的最小整数为4。

### 1.10.2，代码题解

```java
package com.self.datastructure.z_nowcoder.accidence;

/**
 * NC681: 牛牛找数
 * 牛牛有两个数a和b，他想找到一个大于a且为b的倍数的最小整数，
 * 只不过他算数没学好，不知道该怎么做，现在他想请你帮忙。
 * 给定两个数a和b，返回大于a且为b的倍数的最小整数。
 *
 * @author PJ_ZHANG
 * @create 2021-04-23 15:38
 **/
public class NC681_Multiple {

    public static void main(String[] args) {
        System.out.println(findNumber_1(3, 2));
    }

    /**
     * 先找出 a 对 b 的倍数
     * 以这个倍数为基础进行计算,
     * 如果 b 与这个倍数的积大于 a, 则直接返回
     * 如果 b 与这个倍数的积小于等于 a, 则对这个倍数加1, 肯定大于 a
     * 注意该题有时间复杂度要求
     * @param a
     * @param b
     * @return
     */
    public static int findNumber_1 (int a, int b) {
        int data = a / b;
        if (b * data <= a) {
            return b * (data + 1);
        }
        return b * data;
    }

    /**
     * 时间复杂度超出
     * @param a
     * @param b
     * @return
     */
    public static int findNumber (int a, int b) {
        for (int i = a + 1; i <= Integer.MAX_VALUE; i++) {
            if (i % b == 0)
                return i;
        }
        return -1;
    }

}
```

# 2，简单题目

## 2.1，NC78_反转链表

### 2.1.1，题目描述

> 输入一个链表，反转链表后，输出新链表的表头。
>
> 输入：{1,2,3}
>
> 输出：{3,2,1}

### 2.1.2，题目解析

* 最原始的解决办法就是将链表遍历并添加到集合中，然后通过集合反向遍历重新构建数组，这样资源耗费相对较大

* 可以直接通过一次遍历完成链表反转

* 在遍历链表的第一个节点时，用该节点直接构建一个链表，此时链表只有一个节点

  ![1619164990308](E:\gitrepository\study\note\image\刷题_nowcoder\1619164990308.png)

* 继续遍历第二个节点，将该节点的 `next`，指向上一次遍历构建的链表

  ![1619165075449](E:\gitrepository\study\note\image\刷题_nowcoder\1619165075449.png)

* 以此类推，知道完成全部遍历，即链表完成遍历

  ![1619165151842](E:\gitrepository\study\note\image\刷题_nowcoder\1619165151842.png)

### 2.1.3，代码解析

```java
package com.self.datastructure.z_nowcoder.easy;

/**
 * NC78: 反转链表
 * 输入一个链表，反转链表后，输出新链表的表头。
 * 输入: {1,2,3}
 * 输出: {3,2,1}
 *
 * @author PJ_ZHANG
 * @create 2021-04-23 15:52
 **/
public class NC78_ReverseNode {

    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
        listNode.next = new ListNode(2);
        listNode.next.next = new ListNode(3);
        reverseList(listNode);
        for (;null != listNode;) {
            System.out.println(listNode.val);
            listNode = listNode.next;
        }
    }

    public static ListNode reverseList(ListNode head) {
        ListNode listNode = head;
        if (null == listNode) {
            return listNode;
        }
        // 取第一个作为最后一个节点
        ListNode rootNode = new ListNode(listNode.val);
        ListNode nextNode = listNode.next;
        while (null != nextNode) {
            ListNode preNode = new ListNode(nextNode.val);
            preNode.next = rootNode;
            rootNode = preNode;
            nextNode = nextNode.next;
        }
        return rootNode;
    }
}

class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}
```

## 2.2，两树之和

### 2.2.1，题目描述

> 给出一个整数数组，请在数组中找出两个加起来等于目标值的数，你给出的函数twoSum 需要返回这两个数字的下标（index1，index2），需要满足 index1 小于index2.。注意：下标是从1开始的假设给出的数组中只存在唯一解。
>
> 例如：
>
> 给出的数组为 {20, 70, 110, 150},目标值为90
> 输出 index1=1, index2=2

### 2.2.2，代码题解

```java
package com.self.datastructure.z_nowcoder.easy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * NC61: 两数之和
 * 给出一个整数数组，请在数组中找出两个加起来等于目标值的数，
 * 你给出的函数twoSum 需要返回这两个数字的下标（index1，index2），需要满足 index1 小于index2.。注意：下标是从1开始的
 * 假设给出的数组中只存在唯一解
 * 例如：
 * 给出的数组为 {20, 70, 110, 150},目标值为90
 * 输出 index1=1, index2=2
 *
 * @author PJ_ZHANG
 * @create 2021-04-23 16:07
 **/
public class NC_61TwoDataSum {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(twoSum_1(new int[] {20, 70, 110, 150}, 260)));
    }

    /**
     * 第二种算法: 引入Map
     * 一次遍历, 在Map中进行差值记录, 满足条件后返回
     * * 遍历到某一个值时, 取目标值和该值的差值, 存储到 Map 中记录
     * * 表示该值需要一个怎样的值来组成 target, value为该值索引
     * * 在遍历到每一个数据的时候, 先从 map 中以自身为 key 进行匹配
     * * 如果匹配成功说明, 则直接返回 key 对应的 value为index1, 该值索引为index2
     * * 如果没有匹配成功, 则重复第一步, 存储差值, 进行遍历
     * * 最后注意返回的索引全部+1
     * 时间复杂度为O(n)
     *
     * @param numbers
     * @param target
     * @return
     */
    public static int[] twoSum_1 (int[] numbers, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            int otherData = target - numbers[i];
            if (map.containsKey(numbers[i])) {
                return new int[] {map.get(numbers[i]) + 1, i + 1};
            } else {
                map.put(otherData, i);
            }
        }
        return new int[0];
    }

    /**
     * 第一种算法: 暴力算法
     * 双循环进行集合遍历, 计算每两种组合的数据和, 满足要求直接返回
     * 时间复杂度为O(n2)
     *
     * @param numbers
     * @param target
     * @return
     */
    public static int[] twoSum (int[] numbers, int target) {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                if (numbers[i] + numbers[j] != target) {
                    continue;
                }
                // 索引从0开始, 计数从1开始, 所以加1
                return new int[] {i + 1, j + 1};
            }
        }
        return new int[0];
    }

}

```

## 2.3，NC33_合并有序链表

### 2.3.1，题目描述

> 将两个有序的链表合并为一个新链表，要求新的链表是通过拼接两个链表的节点来生成的，且合并后新链表依然有序。
>
> 输入：{2},{1}
>
> 输出：{1,2}

### 2.3.2，代码题解

```java
package com.self.datastructure.z_nowcoder.easy;

/**
 * NC33: 合并有序链表
 * 将两个有序的链表合并为一个新链表，要求新的链表是通过拼接两个链表的节点来生成的，且合并后新链表依然有序。
 * 输入: {1},{2}
 * 输出: {1,2}
 *
 * @author PJ_ZHANG
 * @create 2021-04-23 16:23
 **/
public class NC33_MergeOrderNode {

    public static void main(String[] args) {
        ListNode listNode = new ListNode(-24);
        listNode.next = new ListNode(-18);
        listNode.next.next = new ListNode(6);
        listNode.next.next.next = new ListNode(16);
        ListNode listNode2 = new ListNode(-17);
        ListNode result = mergeTwoLists(listNode, listNode2);
        for (; null != result; ) {
            System.out.println(result.val);
            result = result.next;
        }
    }

    /**
     * 合并两个有序链表到一个新的链表
     * * 首先先定义一个头结点为空的链表, 作为目标有序链表
     * * 然后对两个有序链表都不为空情况下进行比较,
     * * 将较小值加到目标链表的next位, 并递进取值链表和目标链表
     * * 将两个有序链表都不为空的情况都遍历完成后, 此时可能存在最多链表为空
     * * 将该链表直接整体添加到目标链表的 next 即可
     *
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (null == l1) {
            return l2;
        }
        if (null == l2) {
            return l1;
        }
        ListNode listNode = new ListNode(0);
        ListNode currNode = listNode;
        for (; null != l1 && null != l2; ) {
            // 都不为空时处理
            if (l1.val > l2.val) {
                currNode.next = l2;
                currNode = currNode.next;
                l2 = l2.next;
            } else {
                currNode.next = l1;
                currNode = currNode.next;
                l1 = l1.next;
            }
        }
        // l1为空时的l2处理
        if (null == l1) {
            currNode.next = l2;
        }
        // l2为空时的l1处理
        if (null == l2) {
            currNode.next = l1;
        }
        return listNode.next;
    }

}

//class ListNode {
//    int val;
//    ListNode next = null;
//    ListNode(int val) { this.val = val; }
//}
```

## 2.4，●NC76_用两个栈实现队列

### 2.4.1，题目描述

> 用两个栈来实现一个队列，完成队列的Push和Pop操作。 队列中的元素为int类型。

### 2.4.2，代码题解

```java
package com.self.datastructure.z_nowcoder.easy;

import java.util.Stack;

/**
 * NC76: 用两个栈模拟队列
 * 首先: Stack是先进后出, 队列是先进先出的
 * 所以通过Stack实现队列, 需要保证push到栈顶 但是pop需要从栈底开始
 * 这时候就需要第二个Stack, 在pop的时候, 将Stack1的数据先出栈再入栈, 就能从Stack2上实现队列的FIFO
 * 一次将Stack1的数据拷贝到Stack2, 如果Stack2里面数据 pop 完, 则触发再次拷贝
 *
 * @author PJ_ZHANG
 * @create 2021-04-23 17:10
 **/
public class NC76_TwoStack2List {

    static Stack<Integer> stack1 = new Stack<Integer>();

    static Stack<Integer> stack2 = new Stack<Integer>();

    public static void main(String[] args) {
        push(1);
        push(2);
        push(3);
        System.out.println(pop());
        System.out.println(pop());
        System.out.println(pop());
    }

    /**
     * 数据入栈, 直接推数据即可
     * @param node
     */
    public static void push(int node) {
        stack1.push(node);
    }

    /**
     * 模拟队列进行数据出栈
     * @return
     */
    public static int pop() {
        // 如果栈2以空, 将栈1的数据导入到栈2
        if (stack2.isEmpty()) {
            for (; !stack1.isEmpty(); ) {
                stack2.push(stack1.pop());
            }
        }
        // 因为栈是先进后出, 经过栈1到栈2导数据, 此时栈2栈顶数据即为先进数据
        // 直接弹数据, 先进数据先出, 满足队列要求
        return stack2.pop();
    }

}
```

## 2.5，●NC68_跳台阶

### 2.5.1，题目描述

> 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）

### 2.5.2，题目解析

1. 首先找规律
   * F(1) = 1 {1}
   * F(2) = 2 {1+1, 2}
   * F(3) = 3 {1+1+1, 1+2, 2+1}
   * F(4) = 5 {1+1+1+1, 2+2, 2+1+1, 1+2+1, 1+1+2}
   * F(N) = F(N - 1) + F(N - 2)

2. 其次进行逻辑逆推
   * 假如现在需要直接上到第6阶台阶
   * 上去的方式只有两种
     * 从第5阶台阶跳一阶跳上去
     * 从第4阶台阶跳两阶跳上去
   * 第6阶 = 第4阶 + 第5阶
   * 以此类推，直到递归到第1阶和第2阶的初始化，最后计算获取最终结果
   * <font color=red>相对来讲，递归很慢！！！</font>

### 2.5.3，代码题解

```java
package com.self.datastructure.z_nowcoder.easy;

/**
 * NC68: 跳台阶
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。
 * 求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）
 *
 * 首先找规律
 * * F(1) = 1 {1},
 * * F(2) = 2 {1+1, 2},
 * * F(3) = 3 {1+1+1, 1+2, 2+1},
 * * F(4) = 5 {1+1+1+1, 2+2, 2+1+1, 1+2+1, 1+1+2}
 * * F(N) = F(N - 1) + F(N - 2)
 *
 * 其次可以逆向推理
 * * 假如现在需要直接上到第6阶台阶
 * * 上去的方式只有两涨
 * *** 1, 从第5阶台阶跳一阶跳上去
 * *** 2, 从第4阶台阶跳两阶跳上去
 * * 无论是第4阶和第5阶台阶都可以上到第6阶台阶
 * * 第6阶台阶的上法就是上第4阶台阶的上法和第5阶台阶的上法之和
 * * 向下以此类推, 首先明确 F(1) = 1, F(2) = 2
 * * F(6) = F(4) + F(5) = 5 + 8 = 13
 *
 * @author PJ_ZHANG
 * @create 2021-04-25 15:02
 **/
public class NC68_JumpFloor {

    public static void main(String[] args) {
        // System.out.println(jumpFloor(100));
        System.out.println(jumpFloor_1(40));
    }

    /**
     * 非递归方式处理
     * 递归方式是从顶往下逆推取最终结果值
     * 非递归方式是从下向上顺序叠加
     *
     * 已知第1阶有一种跳法, 第2阶有两种跳法
     * 第N阶有 F(N - 1) + F(N - 2) 中跳法
     * 那么可以对前两个数据进行迭代保存, 循环获取第N个数的大小
     *
     * @param target
     * @return
     */
    public static int jumpFloor_1(int target) {
        if (target <= 2) {
            return target;
        }
        int first = 1, second = 2;
        int result = 0;
        for (int i = 3; i <= target; i++) {
            result = first + second;
            first = second;
            second = result;
        }
        return result;
    }

    /**
     * 递归处理
     * 无论目标是多少台阶, 直接取前一阶跳法和前两阶跳发的和
     * 在目标台阶为1和2时, 取定值跳出即可
     *
     * @param target
     * @return
     */
    public static int jumpFloor(int target) {
        if (target <= 2) {
            return target;
        }
        return jumpFloor(target - 1) + jumpFloor(target - 2);
    }

}
```

## 2.6，●NC19_子数组的最大累加和问题

### 2.6.1，题目描述

> 给定一个数组arr，返回子数组的最大累加和
>
> 例如，arr = [1, -2, 3, 5, -2, 6, -1]，所有子数组中，[3, 5, -2, 6]可以累加出最大的和12，所以返回12.
>
> 题目保证没有全为负数的数据
>
> [要求]：时间复杂度为*O*(*n*)，空间复杂度为*O(1)*

### 2.6.2，题目解析

1. <font color=red>注意该题重点：时间复杂度为O(n)，空间复杂度为O(1)</font>
2. 因为是返回子数组的最大累加和，<font color=red>所以最终的最大值，一定是连续的数字累加产生的</font>
3. 可从数组开始依次对数组元素进行累加，并将比较后的结果值赋值给当前位置
4. 当累加值小于当前值时，即 `arr[i - 1] + arr[i] < arr[i]` 时，以 `arr[i]` 作为初始值继续进行累加，并将  `arr[i]` 与最大值进行比较，进行最大值记录
5. 在整个累加过程中，只能顺序走，不能跳着走；

### 2.6.3，题目图解

* 原数组

  ![1619346822406](E:\gitrepository\study\note\image\刷题_nowcoder\1619346822406.png)

* 去第一个元素为初始元素，且为最大元素，与第二个元素累加并比较

  * 最大值：1
  * 累加后值：1 + -2 = -1
  * -1 > -2，则替换该位置值

  ![1619346877364](E:\gitrepository\study\note\image\刷题_nowcoder\1619346877364.png)

* 继续用前两个元素的累加值与第三个元素进行累加比较

  * 当前最大值：1
  * 累加后值：-1 + 3 = 2
  * 2 < 3，则该位置不变
  * 最大值修改为3

  ![1619347440504](E:\gitrepository\study\note\image\刷题_nowcoder\1619347440504.png)

* 继续用第四个元素，与前三个元素的结果进行比较，最大值修改为8

  ![1619347011197](E:\gitrepository\study\note\image\刷题_nowcoder\1619347011197.png)

* 继续第五个元素：注意此时虽然把数组元素变了，但是因为累加值为6，小于最大值8，则最大值不变

  ![1619347057104](E:\gitrepository\study\note\image\刷题_nowcoder\1619347057104.png)

* 继续第六个元素，最大值修改为12

  ![1619347091297](E:\gitrepository\study\note\image\刷题_nowcoder\1619347091297.png)

* 继续第七个元素：虽然数组元素变了，但是因为累加值11小于12，则最大值不变

  ![1619347151002](E:\gitrepository\study\note\image\刷题_nowcoder\1619347151002.png)

* 最终，该数组的最大累加子串为 `[3，5,-2,6]`，最大值为12

### 2.6.4，代码解析

```java
package com.self.datastructure.z_nowcoder.easy;

import java.util.Map;

/**
 * NC19: 子树最大累加和问题
 * 给定一个数组arr，返回子数组的最大累加和
 * 例如，arr = [1, -2, 3, 5, -2, 6, -1]，所有子数组中，[3, 5, -2, 6]可以累加出最大的和12，所以返回12.
 * 要求: 时间复杂度为O(n), 空间复杂度为O(1)
 * <p>
 * 此题最大的难点在于时间复杂度和空间复杂度限制
 * * O(n): 只能有一层循环
 * * O(1): 不能开另外的数组
 * <p>
 * 解题思路:
 * * 首先定义一个初始化的最大值, 默认取 arr[0] 作为最大值
 * * 从1索引开始, 遍历数组数据, 并对数据进行累加,
 * * 用本次累加结果与当前遍历元素进行比较
 * ** 如果大于, 本次累加结果为下次累加的基础值
 * ** 如果小于, 以当前遍历元素为下次累加的基础值
 * * 用本次比较记录的值与记录的最大值进行比较, 将较大值记为最大值
 *
 * * 累加值存储, 可直接存储在遍历的当前位, 为下一次遍历提供支持, 不需要单独定义
 *
 * @author PJ_ZHANG
 * @create 2021-04-25 15:51
 **/
public class NC19_MaxSubArraySum {

    public static void main(String[] args) {
        System.out.println(maxsumofSubarray(new int[]{1, -2, 3, 5, -2, 6, -1, 9}));
    }

    public static int maxsumofSubarray(int[] arr) {
        if (null == arr || 0 == arr.length) {
            return 0;
        }
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            // 默认累加值为前一位, 取 (累加值+当前值) 与 当前值 的较大值作为新值
            arr[i] = Math.max(arr[i - 1] + arr[i], arr[i]);
            // 取累加后的值与最大值的较大值作为最大值
            max = Math.max(arr[i], max);
        }
        return max;
    }

}
```

## 2.7，NC22_合并两个有序数组

### 2.7.1，题目描述

> 给出两个有序的整数数组 A 和 B，请将数组 B 合并到数组 A 中，变成一个有序的数组
>
> 注意：可以假设 A 数组有足够的空间存放 B 数组的元素，A 和 B 中初始的元素数目分别为 m 和 n

### 2.7.2，题目图解

* 原始数组如下，默认 A 数组有足够的空间存放 B 数组的元素：

  ![1619511694454](E:\gitrepository\study\note\image\刷题_nowcoder\1619511694454.png)

* 对于 A 和 B 数组组成的新数组，长度为 `m + n = 7`；因为 A 数组有足够的空间存放 B 数组的元素，所以通过 A 数组来存储 A、B 两个数组的排序元素，从 A 数组的末尾开始存储

* 此时，定义两个指针，分别指向 A、B 两个数组有效元素的最后一位，再定义一个指针指向最终数组的当前操作位置

  ![1619512107249](E:\gitrepository\study\note\image\刷题_nowcoder\1619512107249.png)

* 对指针指向的两个元素进行比较，较大的元素存储在最终数组指针指向的位置；同时将较大元素的指针前移，最终数组指针前移

  ![1619512156793](E:\gitrepository\study\note\image\刷题_nowcoder\1619512156793.png)

* 重复操作，知道数组最终处理完成

### 2.7.3，代码题解

```java
package com.self.datastructure.z_nowcoder.easy;

import java.util.Arrays;

/**
 * NC22: 合并两个有序数组
 * 给出两个有序的整数数组 A 和 B ，请将数组 B 合并到数组 A 中，变成一个有序的数组
 * 可以假设 A 数组有足够的空间存放 B 数组的元素， A 和 B 中初始的元素数目分别为 m 和 n
 *
 * @author PJ_ZHANG
 * @create 2021-04-25 18:55
 **/
public class NC22_CombineTwoSortArray {

    public static void main(String[] args) {
        int[] arrA = new int[]{4, 5, 6, 7, 0, 0, 0};
        int[] arrB = new int[]{1, 2, 3};
        merge_1(arrA, 4, arrB, 3);
        System.out.println(Arrays.toString(arrA));
    }

    /**
     * 第一种方式通过一个一个数据插入, 时间复杂度为O(n2)
     * * 前提条件: A数组有足够的空间存储B数组
     * * A + B数组的数组长度为 m + n
     * * 此时可以将A数组本身作为一个新数组, 该数组长度为m + n
     * * 将A数组的初始元素和B数组从后往前依次加入 (m + n) 的数组长度中
     * * 加入完成后, 数组完成
     *
     * @param A
     * @param m
     * @param B
     * @param n
     */
    public static void merge_1(int A[], int m, int B[], int n) {
        int i = m - 1;
        int j = n - 1;
        int maxIndex = m + n - 1;
        // 第一次循环, 将A, B两个数组元素依次往A数组中添加,
        // 从后往前遍历, 从后往前添加
        for (; i >= 0 && j >= 0; ) {
            A[maxIndex--] = A[i] > B[j] ? A[i--] : B[j--];
        }
        // 从上一步跳出循环后
        // 可能两个数组全部添加完成, 此时已经完成
        // 也可能只有A数组添加完成, B数组还有剩余, 则继续处理B数组
        // 也可能只有B数组添加完成, A数组还有剩余, 因为本就是在A数组上操作, 所以无需再操作
        for (; j >= 0; ) {
            A[maxIndex--] = B[j--];
        }
    }

    /**
     * 比较简单粗暴的方式
     * * 从后往前遍历 B 数组, 将B数组元素插入到A数组中
     * * 在插入A数组的时候, 从后往前遍历与B数组元素进行比较
     * * 如果大于A元素 > B元素, 则将A元素置于 (索引 + 1) 的位置处
     * * 如果 A 元素 <= B元素, 将 B元素置于 (索引 + 1) 的位置处即可
     *
     * @param A
     * @param m
     * @param B
     * @param n
     */
    public static void merge(int A[], int m, int B[], int n) {
        if (n == 0 || n + m > A.length) {
            return;
        }
        // 从 B 数组开始遍历, 倒序遍历
        a:
        for (int i = n - 1; i >= 0; i--) {
            // 与 A 数组进行比较, 倒序处理
            for (int j = m - 1; j >= 0; j--) {
                // 如果B数组元素值小于A数组元素值
                // 则将A数组的元素值后移一位
                if (B[i] < A[j]) {
                    A[j + 1] = A[j];
                } else {
                    // 否则将B数组的元素值插入A数组里面,
                    // 此时A数组实际长度加1
                    // 继续进行下一个B数组元素循环
                    A[j + 1] = B[i];
                    m++;
                    continue a;
                }
            }
            // 如果全部与A数组元素比较完成后, 都没有插入到A数组中
            // 说明B数组该元素最小, 添加到O位置即可
            A[0] = B[i];
            m++;
        }
    }

}
```

## 2.8，●NC4_判断链表中是否有环

### 2.8.1，题目描述

> 判断给定的链表中是否有环。如果有环则返回true，否则返回false。
>
> 你能给出空间复杂度 O(1) 的解法么？
>
> 备注：链表有环，表示该链表是O型链表或者是6型链表
>
> 因为要求空间复杂度为O(1)，所以集合记录方式直接死掉

### 2.8.2，题目图解

* 对于快慢指针方式，在两个指针走到环状区域时：快指针一次走两个节点，慢指针一次走一个节点，两个指针总会相遇，无非是多跑点路的问题，不再画图

* 对于节点自删方式，链表有环：无非是 O 状数组，或者是 6 状数组；无论是 O 还是 6，在环状的原理都是一样的，下面以 O 为例

  ![1619513756765](E:\gitrepository\study\note\image\刷题_nowcoder\1619513756765.png)

* 此时从头结点 1 处开始遍历，1 节点处理完成后，将 `next` 指向自身，继续进行 2、3处理

  ![1619513822205](E:\gitrepository\study\note\image\刷题_nowcoder\1619513822205.png)

* <font color=red>到节点 3 处理完成后，此时 3 节点的 `next` 元素是1，并且是已经被处理过的元素；此时只需要判断 `node = node.next`，即可说明链表成环</font>

  ![1619513931578](E:\gitrepository\study\note\image\刷题_nowcoder\1619513931578.png)

* 自删方式 `next` 节点不能修改为 `null`，如果修改为 `null`，则此时必定返回不成环

### 2.8.3，代码题解

```java
package com.self.datastructure.z_nowcoder.easy;

/**
 * NC4: 判断链表中是否有环
 * 判断给定的链表中是否有环。如果有环则返回true，否则返回false。
 * 你能给出 O(1) 空间复杂度的解法么？
 *
 * @author PJ_ZHANG
 * @create 2021-04-27 16:30
 **/
public class NC4_CyclicChain {

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node4;
        System.out.println(hasCycle_1(node1));
    }

    /**
     * 节点自删方式
     * * 链表成环, 说明链表为O型链表或者为6型链表, 肯定存在尾结点指向前置某一节点
     * * 从头节点开始遍历, 没遍历过一个节点, 将节点的next指向自身
     * * 在后续持续遍历中, 如果存在节点的next为自身, 说明存在环
     * * 如果遇到null, 则肯定不成环退出
     *
     * @param head
     * @return
     */
    public static boolean hasCycle_1(ListNode head) {
        for (;;) {
            if (null == head || null == head.next) {
                return false;
            }
            if (head == head.next) {
                return true;
            }
            // 取出下一个节点备用
            ListNode nextNode = head.next;
            // 将下一个节点指向指向自己, 说明已经处理过
            head.next = head;
            // 继续从下一个节点开始处理
            head = nextNode;
        }
    }

    /**
     * 通过快慢指针进行处理
     * * 定义两个指针, 一个指针一次跳一格, 一个指针一次跳两格
     * * 如果跳格为null, 说明不成环
     * * 如果两个指针相遇, 说明为环
     *
     * @param head
     * @return
     */
    public static boolean hasCycle(ListNode head) {
        if (null == head || null == head.next) {
            return false;
        }
        ListNode oneNode = head;
        ListNode twoNode = head.next.next;
        for (; ; ) {
            if (null == oneNode || null == twoNode || null == twoNode.next) {
                return false;
            }
            if (oneNode == twoNode) {
                return true;
            }
            // oneNode跳一格
            oneNode = oneNode.next;
            // twoNode跳两格
            twoNode = twoNode.next.next;
        }
    }

}
```

## 2.9，NC52_括号序列

### 2.9.1，题目描述

> 给出一个仅包含字符'(',')','{','}','['和']',的字符串，判断给出的字符串是否是合法的括号序列
>
> 括号必须以正确的顺序关闭，"()"和"()[]{}"都是合法的括号序列，但"(]"和"([)]"不合法。
>
> 输入："["，输出：fasle
>
> 输入："[]"，输出：true

### 2.9.2，代码题解

```java
package com.self.datastructure.z_nowcoder.easy;

import java.util.Stack;

/**
 * NC52: 括号序列
 * 给出一个仅包含字符'(',')','{','}','['和']',的字符串，判断给出的字符串是否是合法的括号序列
 * 括号必须以正确的顺序关闭，"()"和"()[]{}"都是合法的括号序列，但"(]"和"([)]"不合法。
 * 输入: "[", 输出: fasle
 * 输入: "[]", 输出: true
 *
 * @author PJ_ZHANG
 * @create 2021-04-27 17:00
 **/
public class NC52_BracketSequence {

    public static void main(String[] args) {
        System.out.println(isValid_1("{()()()()()}"));
    }

    /**
     * 不通过栈完成, 直接通过字符串替换完成
     * * 对括号组合进行无限替换, 即将(){}[]的连续组合替换为""
     * * 当替换到字符串长度不再变化时不再替换
     * * 如果此时字符串中还有元素, 说明不成对的括号
     * * 如果没有, 则说明括号全部成对
     * @param s
     */
    public static boolean isValid_1(String s) {
        for (;;) {
            int len = s.length();
            s = s.replace("()", "");
            s = s.replace("[]", "");
            s = s.replace("{}", "");
            if (len == s.length()) {
                return s.isEmpty();
            }
        }
    }

    /**
     * 通过栈完成,
     * * 在为左括号时, 统一进行括号入栈
     * * 在为右括号时, 弹栈一个元素
     * * 如果弹栈括号与该右括号匹配, 则继续进行下一个括号处理
     * * 如果不匹配, 直接返回 false
     * * 如果遇到右括号, 而栈里已经没有元素, 说明不匹配, 直接 false
     * * 如果元素已经遍历结束, 栈里面还有元素, 说明右括号不足, 直接 false
     *
     * @param s
     * @return
     */
    public static boolean isValid(String s) {
        if (null == s || s.length() <= 1) {
            return false;
        }
        char[] charArray = s.toCharArray();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == '(' || charArray[i] == '[' || charArray[i] == '{') {
                stack.push(charArray[i]);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                Character character = stack.pop();
                if (character == '(' && charArray[i] != ')')
                    return false;
                else if (character == '[' && charArray[i] != ']')
                    return false;
                else if (character == '{' && charArray[i] != '}')
                    return false;
            }
        }
        if (!stack.isEmpty()) {
            return false;
        }
        return true;
    }

}
```

## 2.10，●NC66_两个链表的第一个公共节点

### 2.10.1，题目描述

> 输入两个无环的单链表，找出它们的第一个公共结点。（注意因为传入数据是链表，所以错误测试数据的提示是用其他方式显示的，保证传入数据是正确的）
>
> 备注：存在公共节点的两个链表，可以理解为 Y 型的两个链表

### 2.10.2，题目图解

* 对于两个存在公共节点的两个链表如图：上下两个链表的第一个公共节点为 `20`，在公共节点前，上面链表有4个节点，下面链表有2个节点

  ![1619516771622](E:\gitrepository\study\note\image\刷题_nowcoder\1619516771622.png)

* 可以通过 <font color=red>双指针方式</font> 进行节点判断，通过两个指针分别指向两个链表的头节点，如果两个节点不相等，则继续指向下一个节点

* 此时存在一个问题，公共节点到两个链表头结点的节点数不一致，到公共节点处肯定会错过

* 此时需要进行链表拼接，将下一个链表拼到上一个链表后面，将上一个链表拼到下一个链表后面，此时双方到拼接后的公共节点的距离是相等的（即第2个20的距离是相等的）

  ![1619517110784](E:\gitrepository\study\note\image\刷题_nowcoder\1619517110784.png)

### 2.10.3，代码题解

```java
package com.self.datastructure.z_nowcoder.easy;

import java.util.HashMap;
import java.util.Map;

/**
 * NC66: 两个链表的第一个公共节点
 * 输入两个无环的单链表，找出它们的第一个公共结点。
 * （注意因为传入数据是链表，所以错误测试数据的提示是用其他方式显示的，保证传入数据是正确的）
 * @author PJ_ZHANG
 * @create 2021-04-27 17:17
 **/
public class NC66_TwoChainPublicNode {

    public static void main(String[] args) {
        ListNode oneNode1 = new ListNode(1);
        ListNode oneNode2 = new ListNode(1);
        ListNode oneNode3 = new ListNode(1);
        ListNode twoNode1 = new ListNode(1);
        ListNode twoNode2 = new ListNode(1);
        ListNode publicNode = new ListNode(1);
        oneNode1.next = oneNode2;
        oneNode2.next = oneNode3;
        oneNode3.next = publicNode;
        twoNode1.next = twoNode2;
        System.out.println(FindFirstCommonNode_1(oneNode1, twoNode1).val);
    }

    /**
     * 方式2: 双指针
     * * 分别用两个指针指向两个链表的头节点, 依次向后遍历, 如果相等, 则为第一个公共节点
     * * 这里面存在一个问题
     * ** 如果在第一个公共节点前, 链表1 有3个元素, 链表2 有5个元素
     * ** 此时第一个公共节点肯定找不到
     * * 这种情况下可以下可以考虑将 链表1 拼在 链表2 后面, 链表2 拼在链表1 后面
     * * 这种情况下, 遍历完链表1 后继续遍历 链表2, 遍历完链表2 后继续遍历链表1
     * * 这种极端情况下, 遍历完两个链表的长度和即可全部遍历完成, 找到目标节点
     *
     * @param pHead1
     * @param pHead2
     * @return
     */
    public static ListNode FindFirstCommonNode_1(ListNode pHead1, ListNode pHead2) {
        if (null == pHead1 || null == pHead2) return null;
        ListNode p1 = pHead1;
        ListNode p2 = pHead2;
        for (;p1 != p2;) {
            p1 = p1.next;
            p2 = p2.next;
            if (p1 != p2) {
                if (null == p1)
                    p1 = pHead2;
                if (null == p2)
                    p2 = pHead1;
            }
        }
        return p1;
    }

    /**
     * 方式1: 添加Map集合
     * * 首先遍历第一个链表, 并通过Hash进行存储
     * * 其次遍历第二个链表, 判断Hash中是否存在
     * * Hash中存在, 即为第一个节点
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)
     *
     * @param pHead1
     * @param pHead2
     * @return
     */
    public static ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        // 定义Hash
        Map<ListNode, Object> map = new HashMap<>();
        // 遍历第一个链表, 存储到 Hash 中
        for (;null != pHead1;) {
            map.put(pHead1, null);
            pHead1 = pHead1.next;
        }
        // 遍历第二个 链表, 判断Hash中是否存在
        for (;null != pHead2;) {
            if (map.containsKey(pHead2)) {
                return pHead2;
            }
            pHead2 = pHead2.next;
        }
        return null;
    }

}
```

## 2.11，NC32_求平方根

### 2.11.1，题目描述

> 实现函数 int sqrt(int x).
>
> 计算并返回x的平方根（向下取整）
>
> 输入：2
>
> 输出：1

### 2.11.2，代码题解

```java
package com.self.datastructure.z_nowcoder.easy;

/**
 * NC32: 求平方根
 * 实现函数 int sqrt(int x).
 * 计算并返回x的平方根（向下取整）
 * 算法提示: 二分法
 * 输入:2, 输出:1
 *
 * @author PJ_ZHANG
 * @create 2021-04-28 10:16
 **/
public class NC32_Sqrt {

    public static void main(String[] args) {
        System.out.println(sqrt(1));
    }

    /**
     * 采用二分法处理, 则对数取中值进行处理
     * 该提注意整数溢出问题, 直接从int 提升到 long 进行处理
     *
     * @param x
     * @return
     */
    public static int sqrt(int x) {
        // 特殊数据单独处理
        if (x <= 1) {
            return x;
        }
        // 定义左右边界, 为两个极值
        long left = 0;
        long right = x;
        for (; ; ) {
            // 取中值进行后续处理
            long middle = (left + right) / 2;
            // 如果中值与左右边界相等, 说明已经处理完成没有找到, 该数无法整值平方
            // 取较小值进行返回
            if (middle == left || middle == right) {
                return (int)left;
            }
            if (middle * middle < x) {
                // 值较小, 向右半部分处理
                left = middle;
            } else if (middle * middle > x) {
                // 值较大, 向左半部分处理
                right = middle;
            } else {
                // 相等直接退出
                return (int)middle;
            }
        }
    }

}
```

## 2.12，NC48_在旋转过的有序数组中寻找目标值

### 2.12.1，题目描述

> 给定一个整数数组nums，按升序排序，数组中的元素各不相同。
>
> nums数组在传递给search函数之前，会在预先未知的某个下标 t（0 <= t <= nums.length-1）上进行旋转，让数组变为[nums[t], nums[t+1], ..., nums[nums.length-1], nums[0], nums[1], ..., nums[t-1]]
>
> 比如，数组[0,2,4,6,8,10]在下标2处旋转之后变为[6,8,10,0,2,4]
>
> 现在给定一个旋转后的数组nums和一个整数target，请你查找这个数组是不是存在这个target，如果存在，那么返回它的下标，如果不存在，返回-1
>
> 输入：[6,8,10,0,2,4],10
>
> 输出：2

### 2.12.2，代码题解

```java
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
```

## 2.13，NC7_股票交易

### 2.13.1，题目描述

> 假设你有一个数组，其中第 i  个元素是股票在第 i 天的价格。
>
> 你有一次买入和卖出的机会。（只有买入了股票以后才能卖出）。请你设计一个算法来计算可以获得的最大收益。
>
> 输入：[1,4,2]
>
> 输出：3

### 2.13.2，代码题解

```java
package com.self.datastructure.z_nowcoder.easy;

/**
 * NC7_股票交易
 * 假设你有一个数组，其中第 i 个元素是股票在第 i 天的价格。
 * 你有一次买入和卖出的机会。（只有买入了股票以后才能卖出）。请你设计一个算法来计算可以获得的最大收益。
 * 输入: [1,4,2]
 * 输出: 3
 *
 * @author PJ_ZHANG
 * @create 2021-04-28 11:48
 **/
public class NC7_StockDeal {

    public static void main(String[] args) {
        System.out.println(maxProfit_1(new int[] {1,4,2}));
    }

    /**
     * 动态规划, 取每一天的最优值
     * @param prices
     * @return
     */
    public static int maxProfit_1 (int[] prices) {
        // 最大收益默认为0
        int max = 0;
        // 最小买进价格默认为1
        int min = prices[0];
        for (int i = 1; i < prices.length; i++) {
            // 当前价格小于最小买进价格, 进行替换
            if (prices[i] < min) {
                min = prices[i];
            }
            // 用当前价格 - 最小买进价格与最大值进行比
            // 取较大值作为最大收益
            max = Math.max(prices[i] - min, max);
        }
        return max;
    }

    /**
     * 暴力算法, 前面买, 后面卖, 去差值最大值即可
     * 时间复杂度: O(n2)
     * @param prices
     * @return
     */
    public static int maxProfit (int[] prices) {
        int max = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            for (int j = i + 1; j < prices.length; j++) {
                // 前面买, 后面卖
                int money = prices[j] - prices[i];
                max = money > max ? money : max;
            }
        }
        return max;
    }

}
```

## 2.14，NC13_二叉树的最大深度

> 求给定二叉树的最大深度，
>
> 最大深度是指树的根结点到最远叶子结点的最长路径上结点的数量。

```java
package com.self.datastructure.z_nowcoder.easy;

import java.util.LinkedList;
import java.util.List;

/**
 * NC13: 二叉树最大深度
 * 求给定二叉树的最大深度，
 * 最大深度是指树的根结点到最远叶子结点的最长路径上结点的数量
 *
 * @author PJ_ZHANG
 * @create 2021-04-28 14:59
 **/
public class NC13_TreeMaxDepth {

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(0);
        treeNode.left = new TreeNode(11);
        treeNode.right = new TreeNode(12);
//        treeNode.left.left = new TreeNode(21);
//        treeNode.left.right = new TreeNode(22);
//        treeNode.right.left = new TreeNode(211);
//        treeNode.right.left.left = new TreeNode(311);
        System.out.println(maxDepth_1(treeNode));
    }

    /**
     * 非递归方式
     * * 逐层进行数据遍历, 去第一层数据后, 将数据存储在集合中, 此时高度为1
     * * 第一层处理完后, 集合中数据为第一层数据
     * * 遍历集合, 取第一层数据的第二层数据, 加入到集合中, 此时高度为2, 并移除第一层数据
     * * 以此类推, 知道集合中没有数据, 此时高度为最大高度
     * @param root
     * @return
     */
    public static int maxDepth_1(TreeNode root) {
        if (null == root) {
            return 0;
        }
        LinkedList<TreeNode> lstNode = new LinkedList<>();
        lstNode.add(root);
        int depth = 0;
        for (;!lstNode.isEmpty();) {
            depth++;
            int length = lstNode.size();
            for (int i = 0; i < length; i++) {
                TreeNode node = lstNode.poll();
                if (null != node.left) lstNode.add(node.left);
                if (null != node.right) lstNode.add(node.right);
            }
        }
        return depth;
    }

    /**
     * 递归方式
     * * 分别获取左子树和右子树的高度
     * * 取较深子树值, 加上根节点的高度返回
     * @param root
     * @return
     */
    public static int maxDepth(TreeNode root) {
        if (null == root) {
            return 0;
        }
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        return left > right ? left + 1 : right + 1;
    }

}

class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;
    public TreeNode(int val) {this.val = val;}
}
```

