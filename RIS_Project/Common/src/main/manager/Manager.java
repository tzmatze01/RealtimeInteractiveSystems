package main.manager;

import main.handler.NetworkMessageHandler;

public abstract class Manager implements Runnable {

    private boolean isAlive;

    public void setAlive(boolean isAlive){ this.isAlive = isAlive; }

    public abstract void register(NetworkMessageHandler nmh);


}
