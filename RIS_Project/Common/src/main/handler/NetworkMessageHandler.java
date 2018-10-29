package main.handler;

import main.messages.Message;
import main.messages.type.MessageType;

public abstract class NetworkMessageHandler<T extends Message> implements Runnable {

    private T handledMessage;

    // returns Type of the handled messages
    public MessageType getHandledMessageType() { return this.handledMessage.getType(); }

    // computation of the messages
    public abstract void handle(T message);
}
