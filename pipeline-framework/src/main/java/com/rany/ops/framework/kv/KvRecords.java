package com.rany.ops.framework.kv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 流程数据抽象
 * 封装KVRecord列表
 *
 * @author dick
 */
public class KvRecords implements Serializable {

    private final static Logger logger = LoggerFactory.getLogger(KvRecords.class);

    private List<KvRecord> kvRecords;

    public KvRecords copy() {
        KvRecords kvRecords = new KvRecords();
        Iterator iterator = this.kvRecords.iterator();

        while (iterator.hasNext()) {
            KvRecord kvRecord = (KvRecord) iterator.next();
            kvRecords.addRecord(kvRecord.copy());
        }
        return kvRecords;
    }

    public KvRecords() {
        kvRecords = new ArrayList<>();
    }

    public int getRecordCnt() {
        return kvRecords == null ? 0 : kvRecords.size();
    }

    public boolean isEmpty() {
        return kvRecords == null || kvRecords.isEmpty();
    }

    public KvRecord getRecord(int index) {
        if (index < 0 || index >= getRecordCnt()) {
            logger.error("get index[{}] is not in range[{}, {})", index, 0, getRecordCnt());
            return null;
        }
        return kvRecords.get(index);
    }

    public void addRecord(KvRecord kvRecord) {
        if (kvRecord == null) {
            logger.warn("kv record is null");
            return;
        }
        kvRecords.add(kvRecord);
    }

    public void addRecords(KvRecords kvRecords) {
        if (kvRecords == null || kvRecords.isEmpty()) {
            logger.warn("kv records is null or is empty");
            return;
        }
        this.kvRecords.addAll(kvRecords.kvRecords);
    }

    public void clear() {
        kvRecords.clear();
    }

    @Override
    public String toString() {
        int recordCnt = getRecordCnt();
        StringBuffer buffer = new StringBuffer();
        buffer.append("kv record num[").append(recordCnt).append("]").append("\n");
        for (int i = 0; i < recordCnt; i++) {
            buffer.append(getRecord(i).toString()).append("\n");
        }
        return buffer.toString();
    }

}
