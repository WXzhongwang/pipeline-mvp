package com.rany.ops.framework.log;

/**
 * SLS 默认keys
 *
 * @author dick
 * @description SLS 默认keys
 * @date 2021/12/20 10:27 下午
 * @email 18668485565@163.com
 */

public final class LoggerKeys {
    private LoggerKeys() {
    }

    /**
     * 日志写入时间
     */
    public static final String SLS_LOGGER_TIME = "sls_logger_time";
    /**
     * 数据开始处理的时间
     */
    public static final String SLS_PROCESS_TIME_MS = "sls_process_time_ms";
    /**
     * 开始处理时间
     */
    public static final String SLS_START_PROCESS_TIME_MS = "sls_start_process_time_ms";
    /**
     * 流经哪些流程组件
     */
    public static final String SLS_PROCESS_PLUGINS = "sls_process_plugins";
    /**
     * 各流程组件耗时
     */
    public static final String SLS_PLUGIN_TIMES = "sls_plugin_times";
    public static final String SLS_FILTER_PLUGIN = "sls_filter_plugin";
    public static final String SLS_DISCARD_PLUGIN = "sls_discard_plugin";
    public static final String SLS_EXCEPTION_PLUGIN = "sls_exception_plugin";
    public static final String SLS_ERROR_REASON = "sls_error_reason";
    public static final String SLS_FILTER_REASON = "sls_filter_reason";
    public static final String SLS_DISCARD_REASON = "sls_discard_reason";
}
