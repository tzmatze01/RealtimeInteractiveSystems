package main.messages;

import main.messages.type.KeyEventType;
import main.messages.type.MessageType;
import main.network.ConnectionCookie;

public class KeyEventMessage extends Message {

    private KeyEventType ket;
    private int keyCode;

    public KeyEventMessage(KeyEventType ket, int keyCode, int userID)
    {
        super(MessageType.KEY_EVENT, userID);

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
