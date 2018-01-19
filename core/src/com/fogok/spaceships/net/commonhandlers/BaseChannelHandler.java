package com.fogok.spaceships.net.commonhandlers;

import com.fogok.dataobjects.transactions.utils.SimpleTransactionReader;
import com.fogok.spaceships.net.controllers.NetRootController;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public abstract class BaseChannelHandler extends ChannelInboundHandlerAdapter{

    protected SimpleTransactionReader simpleTransactionReader = new SimpleTransactionReader();
    protected NetRootController netRootController;

    public BaseChannelHandler(NetRootController netRootController) {
        this.netRootController = netRootController;
    }


    /**
     * Получили ответ от сервиса - просто читаем ето все с нашим эксекьютером, он сам знает какой экшн вызвать, т.к. мы сказали это в конструкторе
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        netRootController.readServerChannel(ctx.channel(), msg, simpleTransactionReader);
    }
}
