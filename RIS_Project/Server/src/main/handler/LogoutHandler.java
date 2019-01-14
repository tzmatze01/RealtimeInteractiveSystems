package main.handler;

import main.messages.type.Message;
import main.messages.type.MessageType;
import main.network.ConnectionCookie;

public class LogoutHandler<T extends Message> extends NetworkMessageHandler<T>{

    private ConnectionCookie cc;

    public LogoutHandler(ConnectionCookie cc) {

        this.cc = cc;
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
