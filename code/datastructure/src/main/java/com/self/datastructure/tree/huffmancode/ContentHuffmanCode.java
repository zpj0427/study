package com.self.datastructure.tree.huffmancode;

import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * 霍夫曼编码
 *
 * @author PJ_ZHANG
 * @create 2020-04-07 15:16
 **/
public class ContentHuffmanCode {

    public static void main(String[] args) {
        String content = "i like like like java do you like a java";
        // 数据压缩
        // 先将传递字符串转换为byte数组, 并对每一种ASCII码出现频率进行统计
        // 根据频率大小构造赫夫曼树, 并将每一个叶子节点对应的编码值进行Map映射
        // 将原始字符串转换为赫夫曼编码字符串, 此时转换为一串二进制数字
        // 将这一串二进制数字转换为byte数组, 并准备进行传递
        // 最终传递需要赫夫曼树的Map映射和二进制数子串的byte数组
        // 路径映射,
        Map<Byte, String> pathMap = new HashMap<>(16);
        // 最终获取到的结果如下
        // [-88, -65, -56, -65, -56, -65, -55, 77, -57, 6, -24, -14, -117, -4, -60, -90, 28]
        byte[] encodeBytes = encode(content.getBytes(), pathMap);
        System.out.println("最终生成的十进制传输数据: " + Arrays.toString(encodeBytes));
        // 数据解压
        // 将传递的byte[]数组, 转换为赫夫曼编码的二进制数字串
        // 将二进制数组串, 对照赫夫曼编码字典, 重新转换为字符串
        byte[] decodeBytes = decode(encodeBytes, pathMap);
        System.out.println("解析内容完成: " + new String(decodeBytes));
    }

    /**
     * 反编译文本内容
     * 反编译文本内容与编译文本内容相反
     *
     * @param encodeBytes 传递的十进制数字串
     * @param pathMap 字符到频次映射
     * @return
     */
    public static byte[] decode(byte[] encodeBytes, Map<Byte, String> pathMap) {
        // 首先转换十进制传递数组为二进制数字串
        // 反编译的二进制串: 1010100010111111110010001011111111001000101111111100100101001101110001110000011011101000111100101000101111111100110001001010011011100
        String binaryStr = decodeBinaryStr(encodeBytes);
        // 转换二进制数字串为原始字符的字节数组
        byte[] bytes = decodeContent(binaryStr, pathMap);
        return bytes;
    }

    /**
     * 反编译二进制数字串成功后, 开始进行截取映射字典, 生成byte数组, 以便后续进行文本解析
     *
     * @param binaryStr 二进制数字串
     * @param pathMap 字符和路径映射
     * @return
     */
    private static byte[] decodeContent(String binaryStr, Map<Byte, String> pathMap) {
        // 反转字符和路径映射, 处理为路径映射字符
        Map<String, Byte> path2ByteMap = reverseMap(pathMap);
        // 根据路径一段段截取二进制数字串, 并拼凑为有效的byte码
        byte[] resultBytes = doDecodeContent(binaryStr, path2ByteMap);
        return resultBytes;
    }

    /**
     * 反编译为最终需要的字节码
     * @param binaryStr 二进制自己串
     * @param path2ByteMap 路径到字符的映射
     * @return
     */
    private static byte[] doDecodeContent(String binaryStr, Map<String, Byte> path2ByteMap) {
        // 截取的每一个数字, 添加到集合中

        List<Byte> lstBytes = new ArrayList<>(10);
        for (int i = 0; i < binaryStr.length();) {
            int count = 1;
            for (;;) {
                if (binaryStr.length() < i + count) {
                    break;
                }
                // 以count作为一个标识位, 一直向后移动, 多括进一个字符
                // 如果路径到字符映射中, 包含该路径, 则匹配成功, 并添加该字符到集合
                String currStr = binaryStr.substring(i, i + count);
                if (null != path2ByteMap.get(currStr)) {
                    // 添加字符到集合中
                    lstBytes.add(path2ByteMap.get(currStr));
                    break;
                }

                count++;
            }
            // 匹配成功后, i直接进count位, 进行下一组数据处理
            i += count;
        }
        // 转换集合为数组
        byte[] bytes = new byte[lstBytes.size()];
        int index = 0;
        for (Byte currByte : lstBytes) {
            bytes[index++] = currByte;
        }
        return bytes;
    }

    /**
     * 反转字符串, 反转为<value, key>形式
     *
     * @param pathMap
     * @return
     */
    private static Map<String,Byte> reverseMap(Map<Byte, String> pathMap) {
        Map<String, Byte> path2ByteMap = new HashMap<>(16);
        for (Map.Entry<Byte, String> entry : pathMap.entrySet()) {
            path2ByteMap.put(entry.getValue(), entry.getKey());
        }
        return path2ByteMap;
    }

    /**
     * 反编译为二进制数字串
     * @param encodeBytes 十进制字符
     * @return 二进制数字串
     */
    private static String decodeBinaryStr(byte[] encodeBytes) {
        StringBuilder sb = new StringBuilder();
        boolean isNeedSub = true;
        for (int i = 0; i < encodeBytes.length; i++) {
            if (i == encodeBytes.length - 1 && encodeBytes[i] > 0) {
                isNeedSub = false;
            }
            sb.append(decodeDecimal(isNeedSub, encodeBytes[i]));
        }
        return sb.toString();
    }

    /**
     * 转换
     * @param isNeedSub 是否需要截取
     * @param encodeByte 当前需要转换的数据
     * @return
     */
    private static String decodeDecimal(boolean isNeedSub, int encodeByte) {
        String str = "";
        // 此处负数通过二进制转换会转换为标准的32位, 但是正数不会补0
        // 所以需要对数据转换后再截取, 转换方式为与256进行或运算
        // 256的二进制为: 1 0000 0000, 无论任务数组与256进行或运算后, 绝对能保证第九位的1, 则后八位有效
        // 转换完成后, 截取后八位作为有效数据
        // 注意: 最后一位需要处理的数据不一定满8位, 所以不满八位的情况下一定为正数, 需要原样处理
        // 满八位后, 可能为负数, 需要进行判断是否截图, 在调用方法中已经加标识位判断
        if (isNeedSub) {
            encodeByte |= 256;
            str = Integer.toBinaryString(encodeByte);
            str = str.substring(str.length() - 8);
        } else {
            str = Integer.toBinaryString(encodeByte);
        }
        return str;
    }

    /**
     * 编译文本内容
     *
     * @param bytes 文本内容字节码
     * @param pathMap 字符Byte到赫夫曼码的映射
     * @return
     */
    public static byte[] encode(byte[] bytes, Map<Byte, String> pathMap) {
        // 统计频次, 以频次作为构建赫夫曼节点的权值
        Map<Byte, Integer> timeMap = new HashMap<>(16);
        statisticsTime(bytes, timeMap);
        // 转换频次映射Map为List
        List<Node> lstNode = transformMap2List(timeMap);
        // 转换为赫夫曼树
        Node huffmanTree = encodeHuffmanTree(lstNode);
        // 根据赫夫曼树, 生成字符的映射路径
        encodeByte2Path(huffmanTree, pathMap);
        // 根据传递内容, 拼接赫夫曼编码的二进制串, 按照上面传递的字符, 长度应该为133
        // 另外不同方式方式构建的赫夫曼树, 获得的串不一致
        // 比如形同time值的不同数据, 放在list的不同位置, 拼出来的树不一样, 但带权路径一样
        String binaryStr = encodeBinaryStr(bytes, pathMap);
        // 构建完成二进制串后, 对二进制串每8位生成一个十进制数据进行传递, 并转换为byte
        // 此处主要为了减少传递数据
        byte[] resultData = encodeResultData(binaryStr);
        return resultData;
    }

    /**
     * 对二进制数字串, 每8位构造一个十进制数据, 并传递出去,
     * 这一步构造的数据, 是真正需要传递出去的数据
     *
     * @param binaryStr
     * @return
     */
    private static byte[] encodeResultData(String binaryStr) {
        // 获取长度
        int length = (binaryStr.length() + 7) / 8;
        int count = 0;
        int index = 0;
        byte[] bytes = new byte[length];
        // 截取长度进行处理
        for (int i = 0; i < length; i++) {
            String currStr = "";
            if (i == length - 1) {
                currStr = binaryStr.substring(count);
            } else {
                currStr = binaryStr.substring(count, count + 8);
                count += 8;
            }
            // 截取完成后, 转为byte型
            byte currData = (byte) Integer.parseInt(currStr, 2);
            bytes[index++] = currData;
        }
        return bytes;
    }

    /**
     * 拼接二进制数字串
     * @param bytes 传递字符串转换后的byte
     * @param pathMap byte到二进制路径的映射
     * @return
     */
    private static String encodeBinaryStr(byte[] bytes, Map<Byte, String> pathMap) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(pathMap.get(b));
        }
        return sb.toString();
    }

    /**
     * 根据赫夫曼树, 构建路径映射
     *
     * @param huffmanTree 赫夫曼树
     * @param pathMap 路径映射
     */
    private static void encodeByte2Path(Node huffmanTree, Map<Byte, String> pathMap) {
        StringBuilder sb = new StringBuilder();
        if (null != huffmanTree.getLeftNode()) {
            // 左侧拼接0
            appendPath(huffmanTree.getLeftNode(), "0", sb, pathMap);
        }
        if (null != huffmanTree.getRightNode()) {
            // 右侧拼接1
            appendPath(huffmanTree.getRightNode(), "1", sb, pathMap);
        }
    }

    /**
     * 拼接路径
     *
     * @param node 当前节点
     * @param pathCode 路径值
     * @param sb 拼接字符
     * @param pathMap 映射字符
     */
    private static void appendPath(Node node, String pathCode, StringBuilder sb, Map<Byte, String> pathMap) {
        StringBuilder newSB = new StringBuilder(sb);
        newSB.append(pathCode);
        if (null != node.getLeftNode()) {
            appendPath(node.getLeftNode(), "0", newSB, pathMap);
        }
        if (null != node.getRightNode()) {
            appendPath(node.getRightNode(), "1", newSB, pathMap);
        }
        // 遍历只处理叶子节点, 生成的虚拟父节点, data值为null
        if (null != node.getData()) {
            pathMap.put(node.getData(), newSB.toString());
        }
    }

    /**
     * 转换为赫夫曼树
     *
     * @param lstNode 构造的字符节点集合
     * @return 赫夫曼树根节点
     */
    private static Node encodeHuffmanTree(List<Node> lstNode) {
        for (;CollectionUtils.isNotEmpty(lstNode) && lstNode.size() > 1;) {
            // 每一次循环排序一次, 保证取到的前两个二叉树为最小数据
            Collections.sort(lstNode);
            // 构造父节点, 并设置左右子节点
            Node leftNode = lstNode.get(0);
            Node rightNode = lstNode.get(1);
            Node parentNode = new Node();
            parentNode.setTime(leftNode.getTime() + rightNode.getTime());
            parentNode.setLeftNode(leftNode);
            parentNode.setRightNode(rightNode);
            // 从集合中移除前两个节点
            lstNode.remove(leftNode);
            lstNode.remove(rightNode);
            // 添加新节点
            lstNode.add(parentNode);
        }
        return lstNode.get(0);
    }

    /**
     * 转换映射频次为List, 方便后续赫夫曼树转换
     *
     * @param timeMap
     * @return
     */
    private static List<Node> transformMap2List(Map<Byte, Integer> timeMap) {
        List<Node> lstNode = new ArrayList<>(10);
        for (Map.Entry<Byte, Integer> entry : timeMap.entrySet()) {
            Node node = new Node(entry.getKey(), entry.getValue());
            lstNode.add(node);
        }
        return lstNode;
    }

    /**
     * 统计每一个字符出现的频次
     *
     * @param bytes
     * @param pathMap
     */
    private static void statisticsTime(byte[] bytes, Map<Byte, Integer> timeMap) {
        for (byte currByte : bytes) {
            Integer time = timeMap.get(currByte);
            time = null == time ? 1 : ++time;
            timeMap.put(currByte, time);
        }
    }

    @Data
    static class Node implements Comparable<ContentHuffmanCode.Node> {

        private Byte data; // 字符

        private int time; // 字符频次

        private Node leftNode;

        private Node rightNode;

        public Node(Byte data, int time) {
            this.data = data;
            this.time = time;
        }

        @Override
        public String toString() {
            return "Node: [data = " + data + ", time = " + time + "] ";
        }

        /**
         * 进行数据比较, 满足数据升序排序
         * @param node
         * @return
         */
        @Override
        public int compareTo(Node node) {
            return this.getTime() - node.getTime();
        }
    }

}
