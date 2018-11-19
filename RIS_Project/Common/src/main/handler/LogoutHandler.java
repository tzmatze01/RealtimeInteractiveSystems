package main.handler;

import main.messages.LogoutMessage;
import main.messages.Message;

public class LogoutHandler<T extends Message> extends NetworkMessageHandler<T>{

    @Override
    public void handleMessage(T message) {

        System.out.println("Logout Handler: "+message.getType());
    }


}
