package main.handler;

import main.messages.Message;
import main.messages.type.MessageType;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public abstract class NetworkMessageHandler<T extends Message> implements Runnable {


    private final BlockingQueue<T> messageQueue = new LinkedBlockingDeque<>();
    private boolean isAlive = true;

    public void addMessage(T message) { this.messageQueue.offer(message); }

    public void setAlive(boolean isAlive) { this.isAlive = isAlive; }

    public boolean isAlive() { return isAlive; }

    public abstract void handleMessage(T message);

    // returns Type of the handled messages
    public abstract MessageType getHandledMessageType();

    @Override
    public void run()
    {
        while(isAlive)
        {
            try
            {
                // take() blocks when there is no message in queue
                T message = messageQueue.take();
                handleMessage(message);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
