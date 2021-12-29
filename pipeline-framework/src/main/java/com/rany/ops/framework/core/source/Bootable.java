package com.rany.ops.framework.core.source;

/**
 * Bootable
 * <p>
 * 标识该插件可启动，用于source
 *
 * @author dick
 * @description Bootable
 * @date 2021/12/16 2:11 下午
 * @email 18668485565@163.com
 */

public interface Bootable {

    /**
     * 启动source
     *
     * @return
     */
    boolean run();
}
