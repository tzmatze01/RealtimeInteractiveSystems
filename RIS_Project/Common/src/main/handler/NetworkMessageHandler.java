package main.handler;

import main.messages.Message;
import main.messages.type.MessageType;

public abstract class NetworkMessageHandler<T extends Message> implements Runnable {

    private T handledMessage;

    // returns Type of the handled messages
    public MessageType getHandledMessageType() { return this.handledMessage.getType(); }

    // computation of the messages
    public abstract void handle(T message);
        // puts message in Queue to calculate later in run method?

        // TODO idee: 'shooter' mit grundmelodie riff, jeder abgeschossene gegner ergibt weiteren ton.
        // jeder neue spieler ist ein weiteres instrument
}
