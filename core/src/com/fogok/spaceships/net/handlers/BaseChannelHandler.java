package com.fogok.spaceships.net.handlers;

import com.fogok.dataobjects.transactions.utils.SimpleTransactionReader;
import com.fogok.spaceships.net.NetRootController;
import com.fogok.spaceships.net.exception.DefaultExceptionCallBack;

import java.util.concurrent.TimeUnit;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public abstract class BaseChannelHandler extends ChannelInboundHandlerAdapter{

    public static final long CONNECTION_TIMEOUT_MILLISECONDS = 6000;

    protected SimpleTransactionReader simpleTransactionReader = new SimpleTransactionReader();
    protected NetRootController netRootController;
    private volatile boolean isChannelCompleteAction;
    private volatile boolean isConnectActive;
    protected DefaultExceptionCallBack ex;

    public BaseChannelHandler(NetRootController netRootController) {
        this.netRootController = netRootController;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        isConnectActive = true;
    }

    /**
     * Получили ответ от сервиса - просто читаем ето все с нашим эксекьютером,
     * он сам знает какой экшн вызвать, т.к. мы сказали это в конструкторе
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] response = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(response);
        netRootController.readServerChannel(ctx.channel(), response, simpleTransactionReader, null);
        byteBuf.release();
    }

    /**
     * Запускаем таймер, который отваливает нас от сервиса, если он не ответил нам ничего
     * в течение CONNECTION_TIMEOUT_MILLISECONDS
     */
    protected void startTimeOut(final ChannelHandlerContext ctx){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //wait
                try {
                    TimeUnit.MILLISECONDS.sleep(CONNECTION_TIMEOUT_MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!isChannelCompleteAction) {
                    isChannelCompleteAction = true;
                    ctx.channel().disconnect().addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future) throws Exception {
                            if (ex != null)
                                ex.exceptionConnect(new Throwable("CRITICAL SERVER ERROR\nRESPONSE TIMEOUT EXCEPTION"));
                        }
                    });
                }
            }
        }, this.getClass().getSimpleName() + "-TIMEOUT-THREAD").start();
    }

    /**
     * Этот флаг говорит, что мы приняли правильный ответ от сервиса (тобишь завершили работу
     * с ним), и даже если там произошла ошибка - все равно можем выставлять в тру этот флаг, т.к.
     * обработка ошибок может быть в другом месте.
     */
    public void channelCompleteAction() {
        isChannelCompleteAction = true;
    }

    /**
     * Ошибка, если отключаемся до того, как завершена работа с сервисом.
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        if (!isChannelCompleteAction) {
            if (ex != null)
                ex.exceptionConnect(isConnectActive ? new Throwable("CRITICAL SERVER ERROR\nSERVER DETACHED YOU") :
                        new Throwable("SERVER IS NOT RUNNING"));
            isChannelCompleteAction = true;
        }
    }
}
