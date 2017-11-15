package com.fogok.spaceships.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ServerLogicWrapper {

    public static boolean isThreadOnly;
    public static void openServerSocket(final NetRootController netRootController){
        if (!isThreadOnly) {
            System.out.println("startSocketThread");
            isThreadOnly = true;
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
                                        ch.pipeline().addLast(new NettyHandler(netRootController));
                                        ch.pipeline().addLast(new ExceptionHandler(netRootController));
                                    }
                                });


                        ChannelFuture future = boot.connect("127.0.0.1", 15505).sync();

                        future.channel().closeFuture().sync();

                    } catch (InterruptedException e) {
                        netRootController.getNetHallController().getConnectionCallBack().exceptionConnect();
                        e.printStackTrace();
                    } finally {
                        workingGroup.shutdownGracefully();
                        System.out.println("stopSocketThread");
                        isThreadOnly = false;
                    }

                }
            }).start();
        }
    }




}
