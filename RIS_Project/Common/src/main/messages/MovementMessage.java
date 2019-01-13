package main.messages;

import main.messages.type.KeyEventType;
import main.messages.type.MessageType;

import java.awt.event.KeyEvent;
import java.io.Serializable;

public class MovementMessage extends Message {


    private KeyEventType ket;
    private int keyCode;

    public MovementMessage(KeyEventType ket, int keyCode)
    {
        super(MessageType.MOVEMENT);

        this.ket = ket;
        this.keyCode = keyCode;
    }


    public  KeyEventType getKet() {
        return ket;
    }

    public int getKeyCode() {
        return keyCode;
    }

}
