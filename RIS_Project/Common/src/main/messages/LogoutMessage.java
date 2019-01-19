package main.messages;

import main.messages.type.Message;
import main.messages.type.MessageType;
import main.network.ConnectionCookie;

public class LogoutMessage extends Message {


    public LogoutMessage(int userID)
    {
        super(MessageType.LOGOUT, userID);
    }

}
