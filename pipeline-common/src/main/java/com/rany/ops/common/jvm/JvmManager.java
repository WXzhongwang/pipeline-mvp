package com.rany.ops.common.jvm;

/**
 * JVM管理器
 *
 * @author dick
 * @date 2020-05-13 21:58:28
 */
public class JvmManager {

    /**
     * 获取当前内存统计
     *
     * @return 内存统计
     */
    public static Memory getCurrentMemory() {
        Runtime runtime = Runtime.getRuntime();
        Memory memory = new Memory();
        memory.jvmFreeMemory = runtime.freeMemory();
        memory.jvmHeapMemory = runtime.totalMemory();
        memory.jvmMaxHeapMemory = runtime.maxMemory();
        memory.jvmUseMemory = memory.jvmHeapMemory - memory.jvmFreeMemory;
        return memory;
    }
}
