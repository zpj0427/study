package com.self.datastructure.algorithm.greed;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 贪心算法_电台覆盖问题
 * * 贪心算法是在每一次选择时力求最优解, 最终达到的结果贴近于最优解的算法
 * * 对应存在多个电台, 每个电台可以覆盖多个不同的城市, 各个电台间覆盖城市有重合, 最少需要多少电台就可以实现全覆盖
 * * 1, 首先汇总还未覆盖的全部城市, 第一次汇总即为全部城市
 * * 2, 然后遍历各个电台, 统计该电台在未覆盖的城市中还可以覆盖几个城市
 * * 3, 找出可覆盖最多城市的电台作为最优解, 进行电台记录, 作为选择之一
 * * 4, 从未覆盖的城市集合中移除该电台可覆盖的城市
 * * 5, 重复2,3,4步, 知道未覆盖城市为空
 * * 6, 统计记录的电台, 即为最终解, 注意该最终解不一定为最优解
 * @author PJ_ZHANG
 * @create 2020-07-08 17:36
 **/
public class Greed {

    public static void main(String[] args) {
        Map<String, Set<String>> redioMap = new HashMap<>(16);
        redioMap.put("K1", new HashSet<>(Arrays.asList("北京", "上海", "天津")));
        redioMap.put("K2", new HashSet<>(Arrays.asList("广州", "北京", "深圳")));
        redioMap.put("K3", new HashSet<>(Arrays.asList("成都", "上海", "杭州")));
        redioMap.put("K4", new HashSet<>(Arrays.asList("上海", "天津")));
        redioMap.put("K5", new HashSet<>(Arrays.asList("杭州", "大连")));
        System.out.println(greed(redioMap));
    }

    /**
     * 贪心算法
     * @param redioMap 电台及对应城市
     * @return 选择的电台集合
     */
    private static Set<String> greed(Map<String, Set<String>> redioMap) {
        // 首先汇总还未覆盖的全部城市, 第一次汇总即为全部城市
        Set<String> lstTotalCities = getTotalCities(redioMap.values());
        // 统计选择的电台
        Set<String> lstRedio = new HashSet<>(10);
        // 统计当前批次匹配到城市最大的电台
        String maxKey = null;
        int maxCount = 0;
        // 进行处理
        for (;lstTotalCities.size() > 0;) {
            maxKey = null;
            maxCount = 0;
            // 遍历各个电台, 获取为匹配的城市数量
            for (Map.Entry<String, Set<String>> entry : redioMap.entrySet()) {
                Set<String> lstCities = entry.getValue();
                // 获取匹配的数量
                int currCount = getRetainCount(lstTotalCities, lstCities);
                // 如果当前匹配数量大于0, 说明匹配到, 有资格进行记录
                // 大于当前最大值, 则进行记录
                if (currCount > 0 && currCount > maxCount) {
                    maxKey = entry.getKey();
                    maxCount = currCount;
                }
            }

            // 一次处理完成后, 统计出需要记录的key进行记录, 并移除匹配的城市
            if (StringUtils.isNotEmpty(maxKey)) {
                lstRedio.add(maxKey);
                lstTotalCities.removeAll(redioMap.get(maxKey));
            }
        }
        return lstRedio;
    }

    /**
     * 获取未匹配城市的交集
     * @param lstTotalCities
     * @param lstCities
     * @return
     */
    private static int getRetainCount(Set<String> lstTotalCities, Set<String> lstCities) {
        int count = 0;
        for (String city : lstCities) {
            if (lstTotalCities.contains(city)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 获取全部城市
     * @param values
     * @return
     */
    private static Set<String> getTotalCities(Collection<Set<String>> values) {
        Set<String> lstTotalCities = new HashSet<>(10);
        for (Set<String> lstCurrData : values) {
            lstTotalCities.addAll(lstCurrData);
        }
        return lstTotalCities;
    }

}
