package com.fadu.springboot.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @Auther: wangchun
 * @Date: 2018/7/4 16:02
 */
public class JSONUtils {
    /**
     * 禁止实例化
     */
    private JSONUtils() {
    }

    /**
     * 反格式对象
     *
     * @param jsonString
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T getJsonObject(String jsonString, Type type) {
        Gson gson = new Gson();
        T obj = gson.fromJson(jsonString, type);
        return obj;
    }
}
