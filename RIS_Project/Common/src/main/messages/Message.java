package main.messages;

import main.messages.type.MessageType;

import java.io.Serializable;

public interface Message extends Serializable {

    //private MessageType type;

    /*
    public Message(MessageType type)
    {
        this.type = type;
    }
    */


    MessageType getType();
}
