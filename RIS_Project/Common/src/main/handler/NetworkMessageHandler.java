package main.handler;

import main.messages.Message;
import main.messages.type.MessageType;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public abstract class NetworkMessageHandler<T extends Message> implements Runnable {


    private final BlockingQueue<T> messageQueue = new LinkedBlockingDeque<>();
    private boolean isAlive = true;

    private T handledMessage;

    public abstract void handleMessage(T message);

    public void addMessage(T message) { this.messageQueue.offer(message); }

    public void setAlive(boolean isAlive) { this.isAlive = isAlive; }

    public boolean isAlive() { return isAlive; }

    // returns Type of the handled messages
    public MessageType getHandledMessageType() { return this.handledMessage.getType(); }

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

    // TODO idee: 'shooter' mit grundmelodie riff, jeder abgeschossene gegner ergibt weiteren ton.
    // jeder neue spieler ist ein weiteres instrument
}
