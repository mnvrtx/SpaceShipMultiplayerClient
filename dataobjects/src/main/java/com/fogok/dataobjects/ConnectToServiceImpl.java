package com.fogok.dataobjects;

import com.fogok.dataobjects.transactions.ErrorConnectionToServiceCallback;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import static com.esotericsoftware.minlog.Log.*;

public class ConnectToServiceImpl {

    //region Singleton realization
    private static ConnectToServiceImpl instance;
    public static ConnectToServiceImpl getInstance() {
        return instance == null ? instance = new ConnectToServiceImpl() : instance;
    }
    //endregion

    private int threadsCount;
    public <L extends ChannelInboundHandlerAdapter,
            O extends ChannelDuplexHandler,
            X extends ErrorConnectionToServiceCallback,
            T extends ChannelFutureListener> void connect(final L coreHandler,
                                                          final O exceptionHandler,
                                                          final X errorCallback,
                                                          final T succesCallback,
                                                          final String ip,
                                                          final int port) {
        threadsCount++;
        debug("Start socket thread");
        new Thread(new Runnable() {
            @Override
            public void run() {
                EventLoopGroup workingGroup = new NioEventLoopGroup();

                try {
                    Bootstrap boot = new Bootstrap();
                    boot.group(workingGroup)
                            .channel(NioSocketChannel.class)
                            .option(ChannelOption.TCP_NODELAY, true)
                            .handler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel ch) throws Exception {
                                    ch.config().setRecvByteBufAllocator(new FixedRecvByteBufAllocator(262144)); //set  buf size here
                                    ch.pipeline().addLast(coreHandler);
                                    ch.pipeline().addLast(exceptionHandler);
                                }
                            });


                    ChannelFuture future = boot.connect(ip, port).sync();
                    info(String.format("Connect to service '%s' success", coreHandler.getClass().getSimpleName()));
                    succesCallback.operationComplete(future);
                    future.channel().closeFuture().sync();

                } catch (Exception e) {
                    e.printStackTrace();
                    errorCallback.error(e);
                } finally {
                    workingGroup.shutdownGracefully();
                    info(String.format("Stop connection to '%s' service", coreHandler.getClass().getSimpleName()));
                    threadsCount--;
                }

            }
        }).start();
    }

}