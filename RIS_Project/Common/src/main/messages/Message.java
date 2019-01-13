package main.messages;

import main.messages.type.MessageType;

import java.io.Serializable;

public abstract class  Message implements Serializable {

    private MessageType type;


    public Message(MessageType type)
    {
        this.type = type;
    }



    public MessageType getType() { return this.type; }
}
