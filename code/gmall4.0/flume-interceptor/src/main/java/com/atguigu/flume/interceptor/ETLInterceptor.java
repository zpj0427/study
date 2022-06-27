package com.atguigu.flume.interceptor;

import com.alibaba.fastjson.JSON;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

// 自定义拦截器
public class ETLInterceptor implements Interceptor {
    @Override
    public void initialize() { }

    @Override
    public Event intercept(Event event) {
        byte[] body = event.getBody();
        String message = new String(body, Charset.forName("UTF-8"));
        try {
            // 用JSON进行解析, 如果报错了说明不对
            JSON.parse(message);
            return event;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Event> intercept(List<Event> list) {
        Iterator<Event> iterator = list.iterator();
        for (;iterator.hasNext();) {
            Event event = iterator.next();
            // 遍历每一条数据进行处理, 调用单数据接口, 如果返回null, 说明不对, 直接移除
            if (null == intercept(event))
                iterator.remove();
        }
        return list;
    }

    @Override
    public void close() { }

    // 此外, 需要自定义Builder类, 实现 Interceptor.Builder, 完成拦截器构造
    public static class Builder implements Interceptor.Builder {

        @Override
        public Interceptor build() {
            return new ETLInterceptor();
        }

        @Override
        public void configure(Context context) { }
    }
}
