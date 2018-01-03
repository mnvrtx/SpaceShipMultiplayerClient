package com.fogok.dataobjects.transactions.utils;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fogok.dataobjects.datastates.ClientToServerDataStates;
import com.fogok.dataobjects.datastates.ConnectionToServiceType;
import com.fogok.dataobjects.datastates.ServerToClientDataStates;
import com.fogok.dataobjects.transactions.common.BaseTransaction;
import com.fogok.dataobjects.transactions.authservice.AuthTransaction;
import com.fogok.dataobjects.transactions.authservice.TokenToClientTransaction;
import com.fogok.dataobjects.transactions.common.ConnectionInformationTransaction;
import com.fogok.dataobjects.transactions.common.TokenToServiceTransaction;
import com.fogok.dataobjects.transactions.relaybalancerservice.SSInformationTransaction;
import com.fogok.dataobjects.utils.Serialization;

import java.io.ByteArrayOutputStream;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import static com.esotericsoftware.minlog.Log.info;


public class TransactionExecutor {

    private Output output = new Output(new ByteArrayOutputStream());
    private Input input = new Input();

    private BaseTransaction transactionToFindAppropMethod = new BaseTransaction(ConnectionToServiceType.CLIENT_TO_SERVICE, 0);

    private int lostPackets;

    public ChannelFuture execute(Channel channel, BaseTransaction transaction) {
        output.clear();
        transaction.write(Serialization.getInstance().getKryo(), output);
        ChannelFuture channelFuture = channel.writeAndFlush(Unpooled.copiedBuffer(output.getBuffer()));
        info(String.format("send %s %s to %s",
                transaction.getClass().getSimpleName(), transaction.toString(),
                channel.remoteAddress()));
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

    private AppropriatelyObjectsResolver alternativeTrResolver;

    public void setAlternativeTrResolver(AppropriatelyObjectsResolver alternativeTrResolver) {
        this.alternativeTrResolver = alternativeTrResolver;
    }

    public BaseTransaction findAppropriateTransaction(byte[] bytes){
        BaseTransaction baseTransaction = findAppropriateTransaction(bytes, apprObjClientServerResolver);
        if (baseTransaction == null && alternativeTrResolver != null)
            return findAppropriateTransaction(bytes, alternativeTrResolver);
        return baseTransaction;
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
                case CLIENT_TO_SERVICE:
                    switch (ClientToServerDataStates.values()[baseTransaction.getClientOrServiceToServerDataState()]){
                        case CONNECT_TO_SERVER:
                            return new AuthTransaction(baseTransaction);
                        case TOKEN_WITH_ADDITIONAL_INFORMATION:
                            return new TokenToServiceTransaction(baseTransaction);
                    }
                    break;
                case SERVICE_TO_CLIENT:
                    switch (ServerToClientDataStates.values()[baseTransaction.getClientOrServiceToServerDataState()]) {
                        case TOKEN:
                            return new TokenToClientTransaction(baseTransaction);
                        case SS_INFORMATION:
                            return new SSInformationTransaction(baseTransaction);
                        case CONNECTION_TO_SERVICE_INFORMATION:
                            return new ConnectionInformationTransaction(baseTransaction);
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
