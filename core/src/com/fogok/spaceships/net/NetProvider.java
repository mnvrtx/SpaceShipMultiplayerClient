package com.fogok.spaceships.net;

public class NetProvider {

    private StringBuilder stringBuilder;

    public NetProvider() {

    }

    public String generateResponseJSON(){
        stringBuilder.setLength(0);
        stringBuilder.append("1");
        stringBuilder.append("1");
        stringBuilder.append("1");
        stringBuilder.append("1");
        stringBuilder.append("1");
        stringBuilder.append("1");
        stringBuilder.append("1");
        stringBuilder.append("1");
        stringBuilder.append("1");
        return stringBuilder.toString();
    }
}
