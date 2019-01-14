package main.messages;

import main.messages.type.MessageType;

public class LogoutMessage extends Message {



    public LogoutMessage(int userID)
    {
        super(MessageType.LOGOUT, userID);
    }

}
