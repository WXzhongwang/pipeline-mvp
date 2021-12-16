package com.rany.ops.common.jvm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JVM Memory Monitor
 *
 * @author zhongshengwang
 * @date 2020-08-03 16:27:05
 */
public class MemoryManager {

    private final static Logger logger = LoggerFactory.getLogger(MemoryManager.class);

    private volatile long lastFullGCTimestamp;
    private MemoryManagerConfig config;

    public MemoryManager(MemoryManagerConfig config) {
        this.lastFullGCTimestamp = 0L;
        this.config = config;
    }

    /**
     * 强制执行FullGC
     */
    public void forceExecuteFullGC() {
        if (config == null || !config.enable) {
            logger.warn("memory manager is not enable, ignore executing request");
            return;
        }
        long lastFullGCInterval = System.currentTimeMillis() - lastFullGCTimestamp;
        if (lastFullGCInterval < config.fullGCTimeIntervalMs) { return;}
        synchronized (this) {
            lastFullGCInterval = System.currentTimeMillis() - lastFullGCTimestamp;
            Memory memory = JvmManager.getCurrentMemory();
            double memoryRatio = memory.jvmUseMemory * 1.0d / memory.jvmMaxHeapMemory;
            if (memoryRatio >= config.fullGCMemoryRatio && lastFullGCInterval >= config.fullGCTimeIntervalMs) {
                logger.info("force executing full gc for memory use ratio[{}], current memory[{}]",
                        memoryRatio, memory.toString());
                System.gc();
                lastFullGCTimestamp = System.currentTimeMillis();
            }
        }
    }
}
