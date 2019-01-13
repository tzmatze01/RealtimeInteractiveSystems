package main.handler;

import main.handler.cookie.ConnectionCookie;
import main.messages.LoginMessage;
import main.messages.Message;
import main.messages.type.MessageType;

import java.util.List;

public class LoginHandler<T extends Message> extends NetworkMessageHandler<T> {

    private String[] allowedHosts;
    private ConnectionCookie cc;

    public LoginHandler(String[] allowedHosts, ConnectionCookie cc) {
        this.allowedHosts = allowedHosts;
        this.cc = cc;
    }

    @Override
    public void handleMessage(T message) {

        String userName = ((LoginMessage)message).getUserName();
        System.out.println("search username: "+userName);

        for(String tmpUserName : allowedHosts)
        {
            if(tmpUserName.matches(userName)) {
                System.out.println("found username: " + userName);
                this.cc.setLoggedIn(true);
                System.out.println("cc logged in: " + cc.isLoggedIn());
            }
        }
        System.out.println("end!");
    }

    @Override
    public MessageType getHandledMessageType() {
        return MessageType.LOGIN;
    }


}
