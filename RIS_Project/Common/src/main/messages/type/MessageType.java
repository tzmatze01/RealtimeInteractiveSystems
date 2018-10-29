package main.messages.type;

public enum MessageType {

    MOVEMENT(0),
    LOGIN(1),
    LOGOUT(2);

    private int type;

    MessageType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
