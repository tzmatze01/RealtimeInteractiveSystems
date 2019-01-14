package main.handler;

import main.game.World;
import main.network.ConnectionCookie;
import main.messages.LoginMessage;
import main.messages.Message;
import main.messages.type.MessageType;

public class LoginHandler<T extends Message> extends NetworkMessageHandler<T> {

    private String[] allowedHosts;
    private ConnectionCookie cc;
    private World world;

    public LoginHandler(String[] allowedHosts, ConnectionCookie cc, World world) {
        this.allowedHosts = allowedHosts;
        this.cc = cc;
        this.world = world;
    }

    @Override
    public void handleMessage(T message) {

        String userName = ((LoginMessage)message).getUserName();

        for(String tmpUserName : allowedHosts)
        {
            if(tmpUserName.matches(userName)) {

                //System.out.println("logged in: "+userName);
                // TODO more complex method for userID creation
                int userID = cc.countRegisteredUsers() + 1;
                cc.addUser(userName, userID);

                world.addPlayer(userID);
            }
        }
    }

    @Override
    public MessageType getHandledMessageType() {
        return MessageType.LOGIN;
    }


}
