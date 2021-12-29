package com.rany.ops.framework.config;

import java.io.Serializable;
import java.util.Set;

/**
 * 告警配置
 *
 * @author dick
 * @description 告警配置
 * @date 2021/12/19 8:26 下午
 * @email 18668485565@163.com
 */

public class MonitorConfig implements Serializable {

    private boolean enable;
    private String dingTalkUrl;
    private String dingTalkSecret;
    private Set<String> mobiles;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getDingTalkUrl() {
        return dingTalkUrl;
    }

    public void setDingTalkUrl(String dingTalkUrl) {
        this.dingTalkUrl = dingTalkUrl;
    }

    public String getDingTalkSecret() {
        return dingTalkSecret;
    }

    public void setDingTalkSecret(String dingTalkSecret) {
        this.dingTalkSecret = dingTalkSecret;
    }

    public Set<String> getMobiles() {
        return mobiles;
    }

    public void setMobiles(Set<String> mobiles) {
        this.mobiles = mobiles;
    }
}
