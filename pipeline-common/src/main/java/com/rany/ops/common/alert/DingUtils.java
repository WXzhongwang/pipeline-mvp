package com.rany.ops.common.alert;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author dick
 * @description TODO
 * @date 2021/9/25 12:00 上午
 * @email 18668485565@163.com
 */

public class DingUtils {

    private final static Logger logger = LoggerFactory.getLogger(DingUtils.class);

    private DingUtils() {
    }

    public static boolean send(DingMsgRequest dingMsgRequest) {
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                // 设置连接超时时间
                .readTimeout(20, TimeUnit.SECONDS)
                // 设置读取超时时间
                .build();
        MediaType contentType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(contentType, dingMsgRequest.getBody().toJSONString());
        Request request =
                new Request.Builder().url(dingMsgRequest.getUrl())
                        .post(body)
                        .addHeader("cache-control", "no-cache").build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseContent = response.body().string();
                logger.debug(responseContent);
            }
            return true;
        } catch (IOException e) {
            logger.error("发送机器人消息：", e.getMessage());
        }
        return false;
    }
}
