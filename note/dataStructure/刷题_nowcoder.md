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

### 2.4.1，用两个栈实现队列

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

## 2.5，