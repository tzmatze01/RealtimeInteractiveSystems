package main.handler;

import main.messages.KeyEventMessage;
import main.messages.Message;
import main.messages.type.MessageType;

import java.awt.event.KeyEvent;

public class KeyEventHandler<T extends Message> extends NetworkMessageHandler<T> {


    @Override
    protected void handleMessage(T message) {

        System.out.println("Movement Handler: "+message.getType());

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
    }

    @Override
    public MessageType getHandledMessageType() {
        return MessageType.KEY_EVENT;
    }
}
