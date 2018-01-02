package com.fogok.spaceships.net;

import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyHandler extends ChannelInboundHandlerAdapter {

//    private final static String encoding = "UTF-8";
//    private static boolean blocker;  //не послыаем новые данные, пока не получим старые :) *ВРЕМЕННАЯ ХРЕНЬ, НАДО ЕЁ УБРАТЬ
//
//    public static float TIMEITERSSLEEP = 0.016f;  //in seconds
//
//    private com.fogok.spaceships.net.controllers.NetRootController netRootController;
//
//    public NettyHandler(com.fogok.spaceships.net.controllers.NetRootController netRootController) throws IOException {
//        this.netRootController = netRootController;
//    }
//
//
//    @Override
//    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
//        netRootController.sendLoginAndPassword(ctx);
////        final int sleepTimeMilliSeconds = (int) (TIMEITERSSLEEP * 1000);
//
////        netRootController.getNetHallController().getConnectionCallBack().succesConnect("");
//
////        channel.writeAndFlush(Unpooled.copiedBuffer("l TESTUSER".getBytes(Charset.forName(encoding))));
////        blocker = true;
//
////        channel.eventLoop().scheduleAtFixedRate(new Runnable() {
////            @Override
////            public void run() {
////
////                Gdx.app.postRunnable(new Runnable() {
////                    @Override
////                    public void run() {
////                        if (!blocker) {
////
////
////
//////                            System.out.print("Sending data to server...");
//////                            String dataS = networkData.getJSON();
//////                            ByteBuf byteBuf = Unpooled.copiedBuffer(dataS.getBytes(Charset.forName(encoding)));
//////                            channel.write(byteBuf);
//////                            ctx.flush();
////                            System.out.println("Complete:" );
////
////                            blocker = true;
////                        }
////                    }
////                });
////
////
////
////            }
////        }, sleepTimeMilliSeconds, sleepTimeMilliSeconds, TimeUnit.MILLISECONDS);
//    }
//
//
//
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        netRootController.readServerChannel(msg);
////        final ByteBuf buf = (ByteBuf)msg;
////        Gdx.app.postRunnable(new Runnable() {
////            @Override
////            public void run() {
////                try {
////
////                    byte[] req = new byte[buf.readableBytes()];
////                    buf.readBytes(req);
//////
//////                    final String json = new String(req, encoding);
//////                    networkData.refreshOtherDatas(json);
//////                    System.out.println("Server response:" + json);
////                } catch (Exception e) {
////                    e.printStackTrace();
////                } finally {
////                    buf.release();
////                }
////
////                blocker = false;
////            }
////        });
//    }
//
//    @Override
//    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {    //происходит при отключении от сервера
//        if (netRootController.getNetHallController().getConnectionCallBack() != null)
//            netRootController.getNetHallController().getConnectionCallBack().exceptionConnect();
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        netRootController.getNetHallController().getConnectionCallBack().exceptionConnect();
//        ctx.close();
//    }
}