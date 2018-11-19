package main.handler;

import main.messages.Message;

public class LoginHandler<T extends Message> extends NetworkMessageHandler<T> {

    @Override
    public void handleMessage(T message) {

        System.out.println("Login Handler: "+message.getType());
    }

}
