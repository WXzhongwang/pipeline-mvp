package com.rany.ops.common.json;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author zhongshengwang
 * @description TODO
 * @date 2021/12/22 3:47 下午
 * @email zhongshengwang@shuwen.com
 */

public class CopyUtils {


    /**
     * 深度拷贝JSONArray
     *
     * @param jsonArray
     * @return
     */
    public static JSONArray deepCopy(JSONArray jsonArray) {
        JSONArray replica = new JSONArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            Object value = jsonArray.get(i);
            if (value instanceof JSONObject) {
                replica.add(deepCopy((JSONObject) value));
            } else if (value instanceof JSONArray) {
                replica.add(deepCopy((JSONArray) value));
            } else {
                replica.add(value);
            }
        }
        return replica;
    }

    /**
     * 深度拷贝JSONObject
     *
     * @param jsonObject
     * @return
     */
    public static JSONObject deepCopy(JSONObject jsonObject) {
        JSONObject replica = new JSONObject();
        for (String key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);
            if (value instanceof JSONObject) {
                replica.put(key, deepCopy((JSONObject) value));
            } else if (value instanceof JSONArray) {
                replica.put(key, deepCopy((JSONArray) value));
            } else {
                replica.put(key, value);
            }
        }
        return replica;
    }
}
