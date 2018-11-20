package main.manager;

import main.handler.NetworkMessageHandler;
import main.messages.type.MessageType;

public abstract class Manager {

    private boolean isAlive;

    public void setAlive(boolean isAlive){ this.isAlive = isAlive; }

    public abstract void registerMessageHandler(NetworkMessageHandler nmh);


}
