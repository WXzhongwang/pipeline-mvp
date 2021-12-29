package com.rany.ops.common.alert;

import com.alibaba.fastjson.JSONObject;

/**
 * DingMsgRequest
 *
 * @author dick
 * @description DingMsgRequest
 * @date 2021/9/25 12:10 上午
 * @email 18668485565@163.com
 */

public class DingMsgRequest {

    private String url;

    private JSONObject body;

    public DingMsgRequest(String url, JSONObject body) {
        this.url = url;
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public JSONObject getBody() {
        return body;
    }

    public void setBody(JSONObject body) {
        this.body = body;
    }
}
