package main.handler;

import main.game.World;
import main.messages.InfoMessage;
import main.messages.type.Message;
import main.messages.type.MessageType;
import main.network.ConnectionCookie;

public class InfoHandler<T extends Message> extends NetworkMessageHandler<T> {

    // TODO connection cookie to set game over and lock keyboard input

    private World world;
    private ConnectionCookie cc;

    public InfoHandler(World world, ConnectionCookie cc) {
        this.world = world;
        this.cc = cc;
    }

    @Override
    protected void handleMessage(T message) {

        InfoMessage m = (InfoMessage)message;


        if(m.isDamageMessage())
            world.addPlayerDamage(m.getUserID(), m.getDamage());
        else if(m.isGamePointsMessage())
            world.addPlayerPoints(m.getUserID(), m.getGamePoints());
        else if(m.getLevel() > 0)
            world.setLevel(m.getLevel());
        else if(m.getLevel() == 0)
            cc.isLoggedIn();

        // TODO check userID to find out who lost -> 0 = all won or lost
        //else if(m.isGameOver() && m.isWinGame())
        //else if(m.isGameOver() && !m.isWinGame())


        // TODO nur eigene info messg energy wird angezeigt.

    }

    @Override
    public MessageType getHandledMessageType() {
        return MessageType.INFO_MESSAGE;
    }
}
