package com.self.designmode.flyweight;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 享元工厂, 获取具体享元角色
 * @author PJ_ZHANG
 * @create 2020-08-22 21:16
 **/
public class FlyWeightFactory {

    private static Map<String, FlyWeight> map = new ConcurrentHashMap<>(16);

    private static Map<String, UnsharedFlyWeight> unsharedMap = new ConcurrentHashMap<>(16);

    public static FlyWeight getFlyWeight(String type) {
        if (!map.containsKey(type)) {
            map.put(type, new FlyWeight(type));
        }
        return map.get(type);
    }

    public static UnsharedFlyWeight getUnsharedFlyWeight(String type) {
        if (!unsharedMap.containsKey(type)) {
            unsharedMap.put(type, new UnsharedFlyWeight(type));
        }
        return unsharedMap.get(type);
    }

}
