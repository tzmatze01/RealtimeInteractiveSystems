package main.manager;

import main.handler.NetworkMessageHandler;
import main.messages.type.MessageType;

import java.util.Map;

public interface Manager extends Runnable {

    void setAlive(boolean isAlive);
    boolean getAlive();
    void registerMessageHandler(NetworkMessageHandler nmh);

}
