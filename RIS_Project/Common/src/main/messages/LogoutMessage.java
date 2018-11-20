package main.messages;

import main.messages.type.MessageType;

public class LogoutMessage implements Message {



    public LogoutMessage()
    {
        //super(MessageType.LOGOUT);

    }

    @Override
    public MessageType getType()
    {
        return MessageType.LOGOUT;
    }
}
