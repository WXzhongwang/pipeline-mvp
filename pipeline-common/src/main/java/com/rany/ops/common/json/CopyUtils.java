package com.rany.ops.common.json;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * CopyUtils
 *
 * @author dick
 * @description CopyUtils
 * @date 2021/12/22 3:47 下午
 * @email 18668485565@163.com
 */

public class CopyUtils {

    private CopyUtils() {

    }

    private static final Logger logger = LoggerFactory.getLogger(CopyUtils.class);


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

    /**
     * 深拷贝
     *
     * @param obj
     * @param <T>
     * @return <T>
     */
    public static <T> T deepCopy(T obj) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        ByteArrayInputStream bis = null;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            // 将流序列化成对象
            bis = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bis);
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException exception) {
            logger.error("deep copy failed", exception);
            return null;
        } finally {
            if (null != oos) {
                try {
                    oos.close();
                } catch (IOException ioException) {
                    logger.error("oos closed failed", ioException);
                }
            }
            if (null != ois) {
                try {
                    ois.close();
                } catch (IOException ioException) {
                    logger.error("ois closed failed", ioException);
                }
            }
        }
    }
}
