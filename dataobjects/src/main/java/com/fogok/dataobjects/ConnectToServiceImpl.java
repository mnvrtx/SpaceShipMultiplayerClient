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
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import static com.esotericsoftware.minlog.Log.info;

public class ConnectToServiceImpl {

    //region Singleton realization
    private static ConnectToServiceImpl instance;
    public static ConnectToServiceImpl getInstance() {
        return instance == null ? instance = new ConnectToServiceImpl() : instance;
    }
    //endregion

    public void connect(final ChannelInboundHandlerAdapter coreHandler,
                        final EventLoopGroup workingGroup,
                        final ChannelDuplexHandler exceptionHandler,
                        final ErrorConnectionToServiceCallback errorCallback,
                        final ChannelFutureListener succesCallback,
                        final String ip,
                        final int port, final boolean tcp) {



        new Thread(new Runnable() {
            @Override
            public void run() {
                info("Start netty thread with " + (tcp ? "tcp" : "udp"));
                try {
                    final Bootstrap boot = new Bootstrap();
                    ChannelFuture future;
                    if (tcp) {
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
                        future = boot.connect(ip, port).sync();
                        info(String.format("Connect to service '%s' success",
                                coreHandler.getClass().getSimpleName()));
                    } else {
                        boot.group(workingGroup)
                                .channel(NioDatagramChannel.class)
//                                .option(ChannelOption.SO_BROADCAST, true)
//                                .option(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION, true)
                                .handler(new ChannelInitializer<NioDatagramChannel>() {
                                        @Override
                                        protected void initChannel(NioDatagramChannel ch) throws Exception {
                                            ch.config().setRecvByteBufAllocator(new FixedRecvByteBufAllocator(262144)); //set  buf size here
                                            ch.pipeline().addLast(coreHandler);
                                            ch.pipeline().addLast(new LoggingHandler(LogLevel.TRACE));
                                            ch.pipeline().addLast(exceptionHandler);
                                        }
                                    });
//                        future = boot.bind(0).sync();
                        future = boot.connect(ip, port).sync();
                        info(String.format("Connect to service '%s' success",
                                coreHandler.getClass().getSimpleName()));
                    }

                    succesCallback.operationComplete(future);
                    future.channel().closeFuture().sync();
//                    addListener(new ChannelFutureListener() {
//                        @Override
//                        public void operationComplete(ChannelFuture future) throws Exception {
//                            workingGroup.shutdownGracefully();
//                            info(String.format("Stop connection to service '%s'",
//                                    coreHandler.getClass().getSimpleName()));
//                        }
//                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    errorCallback.error(e);
                } finally {
                    workingGroup.shutdownGracefully();
                    info(String.format("Stop connection to service '%s'",
                            coreHandler.getClass().getSimpleName()));
                }
                info("Stop netty thread");
            }

        }).start();

    }

}
