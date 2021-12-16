package com.rany.ops.pipeline.mvp;

/**
 * @author zhongshengwang
 * @description TODO
 * @date 2021/12/14 10:29 下午
 * @email 18668485565@163.com
 */

public class ChannelPipeline<T extends AbstractContext> {
    /**
     * head
     */
    private ChannelHandlerContext head;

    /**
     * tail
     */
    private ChannelHandlerContext tail;

    /**
     * 渠道类型
     */
    private String channelType;

    public ChannelPipeline(String channelType) {
        this.channelType = channelType;
        this.head = new ChannelHandlerContext("head");
        this.tail = new ChannelHandlerContext("tail");
        head.setNext(tail);
        tail.setPrev(head);
    }

    /**
     * 链表头插入
     *
     * @param handler
     */
    public ChannelPipeline addFirst(String name, ChannelHandler handler) {
        if (handler == null) {
            throw new DeliveryException(DeliveryErrorCode.PARAM_ILLEGAL, "channel handler is null");
        }
        ChannelHandlerContext newCtx = new ChannelHandlerContext(name, handler);
        ChannelHandlerContext headNext = head.getNext();
        newCtx.setPrev(head);
        newCtx.setNext(headNext);
        head.setNext(newCtx);
        headNext.setPrev(newCtx);
        return this;
    }

    /**
     * 链表尾插入
     *
     * @param handler
     */
    public ChannelPipeline addLast(String name, ChannelHandler handler) {
        if (handler == null) {
            throw new DeliveryException(DeliveryErrorCode.PARAM_ILLEGAL, "channel handler is null");
        }
        ChannelHandlerContext newCtx = new ChannelHandlerContext(name, handler);
        ChannelHandlerContext prev = tail.getPrev();
        newCtx.setPrev(prev);
        newCtx.setNext(tail);
        prev.setNext(newCtx);
        tail.setPrev(newCtx);
        return this;
    }

    /**
     * 调用 pipeline
     *
     * @param context context
     */
    public void invokePipeline(T context) {
        if (head.getNext() == null) {
            return;
        }
        head.getNext().invoke(context);
    }
}
