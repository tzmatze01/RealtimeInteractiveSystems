package main.messages;

import main.messages.type.KeyEventType;
import main.messages.type.MessageType;

public class KeyEventMessage extends Message {

    private KeyEventType ket;
    private int keyCode;

    public KeyEventMessage(KeyEventType ket, int keyCode)
    {
        super(MessageType.KEY_EVENT);

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
