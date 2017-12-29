package com.fogok.spaceships.net;

import com.badlogic.gdx.Gdx;
import com.fogok.dataobjects.datastates.ClientState;
import com.fogok.dataobjects.transactions.clientserver.AuthTransaction;
import com.fogok.dataobjects.transactions.utils.TransactionHelper;

import io.netty.channel.ChannelHandlerContext;

public class NetRootController {

    private ClientState clientState;
    private String auid = "";

    private ChannelHandlerContext channelHandlerContext;
    private NetHallController netHallController;
    private NetSessionController netSessionController;

//    private Output output = new Output(new ByteArrayOutputStream());
//    private Input input = new Input(new ByteArrayInputStream(new byte[4096]));

    private TransactionHelper transactionHelper = new TransactionHelper();

    private ReadServerRunnable readServerRunnable = new ReadServerRunnable();

    private class ReadServerRunnable implements Runnable{

        private Object msg;

        @Override
        public void run() {

        }

    }

    public NetRootController() {
        ServerLogicWrapper.isThreadOnly = false;
        netHallController = new NetHallController(this);
        netSessionController = new NetSessionController();
    }

    public void readServerChannel(Object msg){
        readServerRunnable.msg = msg;
        Gdx.app.postRunnable(readServerRunnable);
    }

    public void sendLoginAndPassword(final ChannelHandlerContext channelHandlerContext) throws Exception {
        this.channelHandlerContext = channelHandlerContext;

        transactionHelper.executeTransaction(channelHandlerContext.channel(), new AuthTransaction(netHallController.getLogin(), netHallController.getPassword()));
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
