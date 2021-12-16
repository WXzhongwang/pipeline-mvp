package com.rany.ops.pipeline.mvp.v2;


import com.rany.ops.pipeline.mvp.AbstractContext;

/**
 * @author zhongshengwang
 * @description TODO
 * @date 2021/12/14 10:39 下午
 * @email 18668485565@163.com
 */

public class ChannelHandlerContextV2<T extends AbstractContext, U, V> {

    private ChannelHandlerContextV2 next;

    private ChannelHandlerContextV2 prev;

    private ChannelHandlerV2<U, V> handler;

    public ChannelHandlerContextV2() {
    }

    public ChannelHandlerContextV2(ChannelHandlerV2 handler) {
        this.handler = handler;
    }


    public void invoke(T context, U input) {
        if (context.isSkip()) {
            return;
        }

        V output = null;
        if (this.handler != null) {
            output = handler.process(input);
        }

        if (next != null) {
            next.invoke(context, output);
        }
    }

    public ChannelHandlerContextV2 getNext() {
        return next;
    }

    public void setNext(ChannelHandlerContextV2 next) {
        this.next = next;
    }

    public ChannelHandlerContextV2 getPrev() {
        return prev;
    }

    public void setPrev(ChannelHandlerContextV2 prev) {
        this.prev = prev;
    }

    public ChannelHandlerV2<U, V> getHandler() {
        return handler;
    }

    public void setHandler(ChannelHandlerV2<U, V> handler) {
        this.handler = handler;
    }
}
