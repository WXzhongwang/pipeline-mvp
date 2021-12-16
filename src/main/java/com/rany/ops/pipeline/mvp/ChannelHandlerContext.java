package com.rany.ops.pipeline.mvp;

/**
 * 每个Handler想要注册在pipeline上，
 * 需要通过handler context作为载体，
 * handler context实际上被定义为链表中的一个元素。
 *
 * @author zhongshengwang
 * @description handler上下文
 * @date 2021/12/14 10:08 下午
 * @email 18668485565@163.com
 */

public class ChannelHandlerContext<T extends AbstractContext> {

    private ChannelHandlerContext next;

    private ChannelHandlerContext prev;

    private ChannelHandler handler;

    private final String name;

    public ChannelHandlerContext(String name) {
        this.name = name;
    }

    public ChannelHandlerContext(String name, ChannelHandler handler) {
        this.name = name;
        this.handler = handler;
    }

    public void invoke(T context) {
        if (context.isSkip()) {
            return;
        }

        if (this.handler != null) {
            this.handler.process(context);
            this.handler.digest(context);
        }

        if (next != null) {
            next.invoke(context);
        }
    }

    public ChannelHandlerContext getNext() {
        return next;
    }

    public ChannelHandlerContext getPrev() {
        return prev;
    }

    public ChannelHandler getHandler() {
        return handler;
    }

    public void setNext(ChannelHandlerContext next) {
        this.next = next;
    }

    public void setPrev(ChannelHandlerContext prev) {
        this.prev = prev;
    }

    public void setHandler(ChannelHandler handler) {
        this.handler = handler;
    }
}
