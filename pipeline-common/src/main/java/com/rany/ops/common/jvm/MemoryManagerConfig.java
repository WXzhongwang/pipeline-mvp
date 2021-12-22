package com.rany.ops.common.jvm;

/**
 * @author dick
 * @date 2020-08-03 16:38:00
 */
public class MemoryManagerConfig {

    private final static long DEFAULT_FULL_GC_TIME_INTERVAL_MS = 60000L;
    private final static double DEFAULT_FULL_GC_MEMORY_RATIO = 0.7d;

    public boolean enable = false;
    public long fullGCTimeIntervalMs = DEFAULT_FULL_GC_TIME_INTERVAL_MS;
    public double fullGCMemoryRatio = DEFAULT_FULL_GC_MEMORY_RATIO;
}
