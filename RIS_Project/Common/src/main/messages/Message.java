package main.messages;

import main.messages.type.MessageType;

public abstract class Message {

    private MessageType type;

    public Message(MessageType type)
    {
        this.type = type;
    }

    public MessageType getType()
    {
        return this.type;
    }
}
