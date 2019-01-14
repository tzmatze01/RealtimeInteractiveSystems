package main.messages;

import main.messages.type.MessageType;

import java.io.Serializable;

public abstract class  Message implements Serializable {

    private MessageType type;
    private int userID; // is created by server after successful login, is sent back in loginmessage from server

    public Message(MessageType type, int userID)
    {
        this.type = type;
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }

    public MessageType getType() { return this.type; }
}
