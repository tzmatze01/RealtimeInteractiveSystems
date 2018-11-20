package main.handler;

import main.messages.LogoutMessage;
import main.messages.Message;
import main.messages.type.MessageType;

public class LogoutHandler<T extends Message> extends NetworkMessageHandler<T>{


    public LogoutHandler() {

    }

    @Override
    public void handleMessage(T message) {

        System.out.println("Logout Handler: "+message.getType());
    }

    @Override
    public MessageType getHandledMessageType() {
        return MessageType.LOGOUT;
    }


}
