package com.rany.ops.framework.core.source;

import com.rany.ops.framework.kv.KvRecord;
import com.rany.ops.framework.kv.KvRecords;

import java.util.Map;

/**
 * Fake Source
 *
 * @author dick
 * @description Fake Source
 * @date 2021/12/18 9:11 下午
 * @email 18668485565@163.com
 */

public class FakeSource extends Source {

    private int timeIntervalMs = 1000;
    private boolean running = false;
    private Thread thread;

    public FakeSource(String name) {
        super(name);
    }

    @Override
    protected KvRecord doExecute(KvRecord kvRecord) {
        return kvRecord;
    }

    @Override
    public boolean init(Map<String, Object> config) {
        return true;
    }

    @Override
    public boolean start() {
        logger.info("start fake source...");
        thread = new Thread(this::run, "face source thread");
        thread.start();
        logger.info("start fake source success...");
        return true;
    }

    @Override
    public boolean shutdown() {
        logger.info("fake receiver is stopping ...");
        running = false;
        if (thread != null) {
            thread.interrupt();
            try {
                if (thread.isAlive()) {
                    thread.join();
                }
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return super.shutdown();
    }

    private void run() {
        running = true;
        while (running) {
            KvRecords kvRecords = new KvRecords();
            KvRecord kvRecord = new KvRecord();
            kvRecords.addRecord(kvRecord);
            for (int i = 0; i < kvRecords.getRecordCnt(); i++) {
                KvRecord item = kvRecords.getRecord(i);
                this.execute(item);
            }
            try {
                Thread.sleep(timeIntervalMs);
            } catch (InterruptedException e) {
                logger.error("stop fake source occur an error", e);
            }
        }
    }

}
