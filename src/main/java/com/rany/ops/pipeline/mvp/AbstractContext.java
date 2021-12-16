package com.rany.ops.pipeline.mvp;


import java.util.Map;

/**
 * 在channelPipeline 中每个handler处理上下文可以做一个抽象
 *
 * @author zhongshengwang
 * @description TODO
 * @date 2021/12/14 10:00 下午
 * @email 18668485565@163.com
 */

public abstract class AbstractContext {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3210592194052994445L;

    private final String name;

    /**
     * 是否跳过当前handler
     */
    private boolean skip = false;

    /**
     * 扩展信息
     */
    private Map<String, Object> extendInfo;


    public AbstractContext(String name, boolean skip, Map<String, Object> extendInfo) {
        this.name = name;
        this.skip = skip;
        this.extendInfo = extendInfo;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public Map<String, Object> getExtendInfo() {
        return extendInfo;
    }

    public String getName() {
        return name;
    }
}
