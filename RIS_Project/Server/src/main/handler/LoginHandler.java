package main.handler;

import main.messages.Message;
import main.messages.type.MessageType;

public class LoginHandler<T extends Message> extends NetworkMessageHandler<T> {


    private String[] allowedHosts;

    public LoginHandler(String[] allowedHosts) {
        this.allowedHosts = allowedHosts;
    }

    @Override
    public void handleMessage(T message) {

        System.out.println("Login Handler: "+message.getType());
    }

    @Override
    public MessageType getHandledMessageType() {
        return MessageType.LOGIN;
    }


}
