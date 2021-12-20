package com.rany.ops.framework.log;

/**
 * SLS 默认keys
 *
 * @author zhongshengwang
 * @description SLS 默认keys
 * @date 2021/12/20 10:27 下午
 * @email zhongshengwang@shuwen.com
 */

public final class LoggerKeys {
    private LoggerKeys() {
    }

    public static final String SLS_LOGGER_TIME = "sls_logger_time";
    public static final String SLS_PROCESS_TIME_MS = "sls_process_time_ms";
    public static final String SLS_START_PROCESS_TIME_MS = "sls_start_process_time_ms";
    public static final String SLS_PROCESS_PLUGINS = "sls_process_plugins";
    public static final String SLS_PLUGIN_TIMES = "sls_plugin_times";
    public static final String SLS_FILTER_PLUGIN = "sls_filter_plugin";
    public static final String SLS_DISCARD_PLUGIN = "sls_discard_plugin";
    public static final String SLS_EXCEPTION_PLUGIN = "sls_exception_plugin";
    public static final String SLS_ERROR_REASON = "sls_error_reason";
    public static final String SLS_FILTER_REASON = "sls_filter_reason";
    public static final String SLS_DISCARD_REASON = "sls_discard_reason";
    public static final String SLS_DATA_SUPPLEMENT = "sls_data_supplement";
}
