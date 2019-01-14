package main.handler;

import main.game.World;
import main.messages.KeyEventMessage;
import main.messages.type.Message;
import main.messages.type.KeyEventType;
import main.messages.type.MessageType;
import main.network.ConnectionCookie;

public class KeyEventHandler<T extends Message> extends NetworkMessageHandler<T> {


    private World world;
    private ConnectionCookie cc;

    public KeyEventHandler(World world, ConnectionCookie cc) {
        this.world = world;
        this.cc = cc;

    }


    @Override
    protected void handleMessage(T message) {

        if(((KeyEventMessage)message).getKet() == KeyEventType.KEY_PRESSED) {
            world.keyPressed(message.getUserID(), ((KeyEventMessage) message).getKeyCode());

            System.out.println("Key pressed");
        }
        else if(((KeyEventMessage)message).getKet() == KeyEventType.KEY_RELEASED) {
            System.out.println("Key released");
            world.keyReleased(message.getUserID(), ((KeyEventMessage) message).getKeyCode());
        }


        /*

        int keyCode = ((KeyEventMessage)message).getKeyCode();

        switch( keyCode ) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                System.out.println("w / up");
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                System.out.println("s / down");
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                System.out.println("a / left");
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                System.out.println("d / right");
                break;
            case KeyEvent.VK_SPACE:
                System.out.println("space");
                break;
        }
        */
    }

    @Override
    public MessageType getHandledMessageType() {
        return MessageType.KEY_EVENT;
    }
}
