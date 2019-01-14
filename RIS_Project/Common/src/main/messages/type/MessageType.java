package main.messages.type;

public enum MessageType {

    LOGIN(0),
    LOGOUT(1),
    KEY_EVENT(2),
    NEW_MOVING_OBJECT(3),
    DEL_MOVING_OBJECT(4),
    MOV_MOVING_OBJECT(5);

    private int type;

    
    MessageType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
