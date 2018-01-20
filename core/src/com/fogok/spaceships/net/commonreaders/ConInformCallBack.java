package com.fogok.spaceships.net.commonreaders;

import io.netty.channel.Channel;

public interface ConInformCallBack {
    void receiveResponse(Channel channel, int responseCode);
}
