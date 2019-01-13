package main.messages.type;

public enum MessageType {

    MOVEMENT(0),
    LOGIN(1),
    LOGOUT(2),
    KEY_EVENT(3),
    NEW_MOVING_OBJECT(4);

    private int type;

    
    MessageType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
