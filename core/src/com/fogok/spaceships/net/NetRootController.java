package com.fogok.spaceships.net;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fogok.dataobjects.utils.ClientState;
import com.fogok.dataobjects.utils.ClientToServerDataStates;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

public class NetRootController {

    private ClientState clientState;
    private String auid = "";

    private ChannelHandlerContext channelHandlerContext;
    private NetHallController netHallController;
    private NetSessionController netSessionController;

    private Output output = new Output(new ByteArrayOutputStream());
    private Input input = new Input(new ByteArrayInputStream(new byte[4096]));

    private ReadServerRunnable readServerRunnable = new ReadServerRunnable();

    private class ReadServerRunnable implements Runnable{

        private Object msg;

        @Override
        public void run() {
            final ByteBuf buf = (ByteBuf) msg;
            try {
                byte[] response = new byte[buf.readableBytes()];
                buf.readBytes(response);
                input.setBuffer(response);

                clientState = ClientState.values()[input.readInt(true)];

                System.out.println(clientState.name());

                switch (clientState) {
                    case IN_HALL:
                        auid = input.readString();
                        netHallController.getConnectionCallBack().succesConnect(auid);
                        break;
                }


            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("hueta");
            } finally {
                buf.release();
            }

        }

    }

    public NetRootController() {
        netHallController = new NetHallController(this);
        netSessionController = new NetSessionController();
    }

    public void readServerChannel(Object msg){
        readServerRunnable.msg = msg;
        Gdx.app.postRunnable(readServerRunnable);
    }

    public void sendLoginAndPassword(final ChannelHandlerContext channelHandlerContext) throws InterruptedException {
        this.channelHandlerContext = channelHandlerContext;


        output.writeInt(ClientToServerDataStates.CONNECT_TO_SERVER.ordinal(), true);
        output.writeString(netHallController.getLogin());
        output.writeString(netHallController.getPassword());

        channelHandlerContext.channel().writeAndFlush(Unpooled.copiedBuffer(output.getBuffer()));

        output.clear();

        System.out.println("Write prosol uspesno");
    }


    //region Getters
    public ClientState getClientState() {
        return clientState;
    }

    public ReadServerRunnable getReadServerRunnable() {
        return readServerRunnable;
    }

    public NetHallController getNetHallController() {
        return netHallController;
    }

    public NetSessionController getNetSessionController() {
        return netSessionController;
    }

    //endregion
}
