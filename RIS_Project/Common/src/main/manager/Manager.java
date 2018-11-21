package main.manager;

import main.handler.NetworkMessageHandler;
import main.messages.type.MessageType;

import java.util.Map;

public abstract class Manager implements Runnable {

    protected Map<MessageType, NetworkMessageHandler> listeners;
    protected boolean isAlive = true;

    public void setAlive(boolean isAlive){ this.isAlive = isAlive; }

    public void registerMessageHandler(NetworkMessageHandler nmh)
    {
        if(!listeners.containsKey(nmh.getHandledMessageType()))
        {
            listeners.put(nmh.getHandledMessageType(), nmh);
        }
        else
        {
            // TODO Error messages handler already exists
        }
    }


}
