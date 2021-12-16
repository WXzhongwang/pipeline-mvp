package com.rany.ops.common.jvm;

/**
 * 内存统计
 *
 * @author zhongshengwang
 * @date 2020-05-13 21:54:48
 */
public class Memory {

    public long jvmUseMemory = 0L;
    public long jvmFreeMemory = 0L;
    public long jvmHeapMemory = 0L;
    public long jvmMaxHeapMemory = 0L;

    @Override
    public String toString() {
        return String.format("jvmUseMemory=%d, jvmFreeMemory=%d, jvmHeapMemory=%d, jvmMaxHeapMemory=%d",
                jvmUseMemory, jvmFreeMemory, jvmHeapMemory, jvmMaxHeapMemory);
    }
}
