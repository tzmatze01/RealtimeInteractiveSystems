package main.handler;

import main.game.World;
import main.messages.LoginMessage;
import main.messages.type.Message;
import main.messages.type.MessageType;
import main.network.ConnectionCookie;

public class LoginHandler<T extends Message> extends NetworkMessageHandler<T> {


    private World world;
    private ConnectionCookie cc;


    public LoginHandler(World world, ConnectionCookie cc) {
        this.world = world;
        this.cc = cc;

    }

    @Override
    public void handleMessage(T message) {

        System.out.println("Login Handler: "+message.getType());

        LoginMessage lm = (LoginMessage)message;

        this.cc.addUserPlaying(lm.getUserID(), lm.getUserName());
        world.setPlayerID(lm.getUserID());
        world.setGameStart(true);


    }

    @Override
    public MessageType getHandledMessageType() {
        return MessageType.LOGIN;
    }


}
