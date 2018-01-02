package com.fogok.dataobjects.transactions.utils;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fogok.dataobjects.datastates.ClientToServerDataStates;
import com.fogok.dataobjects.datastates.ConnectionToServiceType;
import com.fogok.dataobjects.datastates.ServerToClientDataStates;
import com.fogok.dataobjects.transactions.BaseTransaction;
import com.fogok.dataobjects.transactions.clientserver.AuthTransaction;
import com.fogok.dataobjects.transactions.serverclient.TokenTransaction;
import com.fogok.dataobjects.utils.Serialization;

import java.io.ByteArrayOutputStream;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import static com.esotericsoftware.minlog.Log.info;


public class TransactionHelper {

    private Output output = new Output(new ByteArrayOutputStream());
    private Input input = new Input();

    private BaseTransaction transactionToFindAppropMethod = new BaseTransaction(ConnectionToServiceType.ClientToService, 0);

    private int lostPackets;

    public ChannelFuture executeTransaction(Channel channel, BaseTransaction transaction) {
        output.clear();
        transaction.write(Serialization.getInstance().getKryo(), output);
        ChannelFuture channelFuture = channel.writeAndFlush(Unpooled.copiedBuffer(output.getBuffer()));
        info(String.format("execute %s %s from %s to %s",
                transaction.getClass().getSimpleName(), transaction.toString(),
                channel.localAddress(), channel.remoteAddress()));
        return channelFuture;
    }

    /**
     * В этом методе мы принимаем в качестве msg конкретный инстанс BaseTransaction. Далее конвертим его в понятный Transaction
     * @param msg ByteBuf
     * @return конкретный новый инстанс BaseTransaction (в возможном будущем с буфера, и скорее всего он будет один юзаться для заполнения
     */
    public BaseTransaction findAppropriateObjectAndCreate(Object msg){
        return findAppropriateObjectAndCreate(msg, apprObjClientServerResolver);
    }

    public BaseTransaction findAppropriateObjectAndCreate(Object msg, AppropriatelyObjectsResolver appropriatelyObjectsResolver) {
        final byte[] bytes = readByteBufAndDispose((ByteBuf) msg);
        BaseTransaction baseTransaction = findAppropriateTransaction(bytes, appropriatelyObjectsResolver);
        if (baseTransaction == null)
            return null;
        if (!fillTransaction(bytes, baseTransaction))
            return null;
        return baseTransaction;
    }

    public BaseTransaction findAppropriateTransaction(byte[] bytes){
        return findAppropriateTransaction(bytes, apprObjClientServerResolver);
    }

    public BaseTransaction findAppropriateTransaction(byte[] bytes, AppropriatelyObjectsResolver appropriatelyObjectsResolver){
        if (!fillTransaction(bytes, transactionToFindAppropMethod))
            return null;
        return appropriatelyObjectsResolver.resolve(transactionToFindAppropMethod);
    }

    public  <T extends BaseTransaction> boolean fillTransaction(byte[] bytes, T baseTransaction) {
        try {
            input.setBuffer(bytes);
            baseTransaction.read(Serialization.getInstance().getKryo(), input);
            return true;
        } catch (Exception e) {
            lostPackets++;
        }
        return false;
    }

    public byte[] readByteBufAndDispose(ByteBuf byteBuf) {
        byte[] response = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(response);
        byteBuf.release();
        return response;
    }

    public int getLostPackets() {
        return lostPackets;
    }

    private static ApprObjResolverClientServerImpl apprObjClientServerResolver = new ApprObjResolverClientServerImpl();

    private static class ApprObjResolverClientServerImpl implements AppropriatelyObjectsResolver{

        @Override
        public BaseTransaction resolve(BaseTransaction baseTransaction) {
            switch (baseTransaction.getConnectionToServiceType()) {
                case ClientToService:
                    switch (ClientToServerDataStates.values()[baseTransaction.getClientOrServiceToServerDataState()]){
                        case CONNECT_TO_SERVER:
                            return new AuthTransaction(baseTransaction);
                    }
                    break;
                case ServiceToClient:
                    switch (ServerToClientDataStates.values()[baseTransaction.getClientOrServiceToServerDataState()]) {
                        case TOKEN:
                            return new TokenTransaction(baseTransaction);
                    }
                    break;
            }
            return null;
        }
    }

    public interface AppropriatelyObjectsResolver { //TODO: возможность переиспользовать болванки транзацкий
        BaseTransaction resolve(BaseTransaction baseTransaction);
    }
}
