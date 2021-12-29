package com.rany.ops.common.json;

import com.rany.ops.framework.kv.KvRecord;
import org.junit.Assert;
import org.junit.Test;

public class CopyUtilsTest {

    @Test
    public void deepCopy() {
        KvRecord kvRecord = new KvRecord();
        kvRecord.put("a", "a");
        KvRecord newRecord = CopyUtils.deepCopy(kvRecord);
        System.out.println(newRecord);
        Assert.assertNotEquals(kvRecord, newRecord);
    }
}